package zingg.common.core.transformer.impl;

import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.client.cols.ZidAndFieldDefSelector;
import zingg.common.client.util.ColName;
import zingg.common.core.context.Context;
import zingg.common.core.preprocess.IPreprocessors;
import zingg.common.core.transformer.ASequentialDataTransformer;

public abstract class PositiveSampleTransformer<S, D, R, C, T> extends ASequentialDataTransformer<S, D, R, C, T> implements IPreprocessors<S, D, R, C, T> {

    private final Context<S, D, R, C, T> context;
    private final IArguments arguments;

    public PositiveSampleTransformer(Context<S, D, R, C, T> context, IArguments arguments) {
        this.context = context;
        this.arguments = arguments;
    }
    @Override
    public ZFrame<D, R, C> processZFrame(ZFrame<D, R, C> data) throws ZinggClientException, Exception {
        if (LOG.isDebugEnabled()) {
            long count = data.count();
            LOG.debug("Total count is " + count);
            LOG.debug("Label data sample size is " + arguments.getLabelDataSampleSize());
        }
        ZFrame<D,R,C> posSample = data.sample(false, arguments.getLabelDataSampleSize());
        //select only those columns which are mentioned in the field definitions
        posSample = getFieldDefColumnsDS(posSample);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sampled " + posSample.count());
        }
        posSample = posSample.cache();
        posSample = preprocess(posSample);
        ZFrame<D,R,C> posPairs = context.getDSUtil().joinWithItself(posSample, ColName.ID_COL, false);

        LOG.info("Created positive sample pairs ");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Pos Sample pairs count " + posPairs.count());
        }
        return posPairs;
    }

    protected ZFrame<D, R, C> getFieldDefColumnsDS(ZFrame<D, R, C> data) {
        ZidAndFieldDefSelector zidAndFieldDefSelector = new ZidAndFieldDefSelector(arguments.getFieldDefinition());
        String[] cols = zidAndFieldDefSelector.getCols();
        return data.select(cols);
    }
}
