package zingg.common.core.transformer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.client.util.ColName;
import zingg.common.client.util.ColValues;
import zingg.common.core.context.Context;
import zingg.common.core.preprocess.IPreprocessors;
import zingg.common.core.transformer.AZFrameTransformer;

public abstract class NegativePairTransformer<S, D, R, C, T> extends AZFrameTransformer<D, R, C> implements IPreprocessors<S, D, R, C, T> {
    private static final Log LOG = LogFactory.getLog(NegativePairTransformer.class);

    private final Context<S, D, R, C, T> context;
    private final IArguments arguments;

    public NegativePairTransformer(Context<S, D, R, C, T> context, IArguments arguments) {
        this.context = context;
        this.arguments = arguments;
    }

    @Override
    public ZFrame<D, R, C> transformZFrame(ZFrame<D, R, C> data) throws ZinggClientException, Exception {
        LOG.debug("input data schema is " +data.showSchema());
        //create 20 pos pairs
        ZFrame<D,R,C> negativePairs = null;
        ZFrame<D,R,C> trFile = getTraining();

        if (trFile != null) {
            trFile = preprocess(trFile).cache();
            ZFrame<D,R,C> trPairs = context.getDSUtil().joinWithItself(trFile, ColName.CLUSTER_COLUMN, true);
            negativePairs = trPairs.filter(trPairs.equalTo(ColName.MATCH_FLAG_COL, ColValues.MATCH_TYPE_NOT_A_MATCH));
            negativePairs = negativePairs.drop(ColName.MATCH_FLAG_COL,
                    ColName.COL_PREFIX + ColName.MATCH_FLAG_COL,
                    ColName.CLUSTER_COLUMN,
                    ColName.COL_PREFIX + ColName.CLUSTER_COLUMN);
            LOG.warn("Total negative pairs found  " + negativePairs.count());
        }


        if (negativePairs!= null) negativePairs = negativePairs.cache();
        return negativePairs;
    }

    private ZFrame<D,R,C> getTraining() {
        return context.getDSUtil().getTraining(context.getPipeUtil(), arguments, context.getModelHelper());
    }

}
