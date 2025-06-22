package zingg.common.core.transformer.pipeline;

import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.core.data.Data;
import zingg.common.core.transformer.IDataTransformer;
import zingg.common.core.transformer.IDataZFrameTransformer;
import zingg.common.core.transformer.IZFrameTransformer;
import zingg.common.core.transformer.map.TransformerMap;

public class MatcherTransformerPipeline<S, D, R, C, T> implements TransformerPipeline<D, R, C> {

    private final TransformerMap transformerMap;
    private ZFrame<D, R, C> testDataOriginal;

    public MatcherTransformerPipeline(TransformerMap transformerMap) {
        this.transformerMap = transformerMap;
    }

    @Override
    public ZFrame<D, R, C> executePipeline(Data<D, R, C> data) throws ZinggClientException, Exception {

        //perform hash transformation
        IDataTransformer<S, D, R, C, T> hashTransformer = (IDataTransformer<S, D, R, C, T>) transformerMap.getTransformer("hashTransformer");
        Data<D, R, C> blockedData = hashTransformer.transform(data);

        //perform block transformation
        IDataZFrameTransformer<D, R, C> blockTransformer = (IDataZFrameTransformer<D, R, C>) transformerMap.getTransformer("blockTransformer");;
        ZFrame<D, R, C> blocks = blockTransformer.transform(blockedData);

        //perform prediction transformation
        IZFrameTransformer<D, R, C> predictionTransformer = (IZFrameTransformer<D, R, C>) transformerMap.getTransformer("predictionTransformer");;
        ZFrame<D, R, C> predictedFrame = predictionTransformer.transform(blocks);

        //perform prediction filter transformation
        IZFrameTransformer<D, R, C> predictionFilterTransformer = (IZFrameTransformer<D, R, C>) transformerMap.getTransformer("predictionFilterTransformer");
        ZFrame<D, R, C> filteredData = predictionFilterTransformer.transform(predictedFrame);

        //perform output transformation
        IDataZFrameTransformer<D, R, C> outputTransformer = (IDataZFrameTransformer<D, R, C>) transformerMap.getTransformer("outputTransformer");;
        ZFrame<D, R, C> identityGraph = outputTransformer.transform(new Data<>(testDataOriginal, filteredData));

        return identityGraph;
    }

    public void setTestDataOriginal(ZFrame<D, R, C> testDataOriginal) {
        this.testDataOriginal = testDataOriginal;
    }
}
