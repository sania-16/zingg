package zingg.common.core.transformer.impl;

import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.core.data.Data;
import zingg.common.core.match.output.IMatchOutputBuilder;
import zingg.common.core.transformer.IDataZFrameTransformer;
import zingg.common.core.transformer.ZFrameTypes;

public class OutputTransformer<S, D, R, C> implements IDataZFrameTransformer<D, R, C> {

    private final IMatchOutputBuilder<S, D, R, C> matchOutputBuilder;

    public OutputTransformer(IMatchOutputBuilder<S, D, R, C> matchOutputBuilder) {
        this.matchOutputBuilder = matchOutputBuilder;
    }

    @Override
    public ZFrame<D, R, C> transform(Data<D, R, C> data) throws Exception, ZinggClientException {
        ZFrame<D, R, C> originalData = data.getZFrameByName(ZFrameTypes.ORIGINAL_DATA.name());
        ZFrame<D, R, C> predictedData = data.getZFrameByName(ZFrameTypes.PREDICTED_DATA.name());
        return matchOutputBuilder.getOutput(originalData, predictedData);
    }
}
