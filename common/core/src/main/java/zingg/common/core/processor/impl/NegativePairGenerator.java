package zingg.common.core.processor.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.client.util.*;
import zingg.common.core.context.IContext;
import zingg.common.core.data.AData;
import zingg.common.core.data.impl.NegativePairs;
import zingg.common.core.preprocess.IPreprocessors;
import zingg.common.core.processor.IProcessor;
import zingg.common.core.processor.enums.ProcessingType;

public abstract class NegativePairGenerator<S, D, R, C, T> implements IProcessor<S, D, R, C, T>, IPreprocessors<S, D, R, C, T> {

    private static final Log LOG = LogFactory.getLog(NegativePairGenerator.class);
    private final IArguments arguments;
    private final IContext<S, D, R, C, T> context;

    public NegativePairGenerator(IArguments arguments, IContext<S, D, R, C, T> context) {
        this.arguments = arguments;
        this.context = context;
    }

    @Override
    public ProcessingType getProcessingType() {
        return ProcessingType.NEGATIVE_PAIR_GENERATOR;
    }

    @Override
    public AData<D, R, C> process(AData<D, R, C> data) throws Exception, ZinggClientException {
        ZFrame<D,R,C> negPairs = null;
        ZFrame<D,R,C> trFile = getTraining();
        if (trFile != null) {
            trFile = preprocess(trFile).cache();
            ZFrame<D,R,C> trPairs = context.getDSUtil().joinWithItself(trFile, ColName.CLUSTER_COLUMN, true);

            negPairs = trPairs.filter(trPairs.equalTo(ColName.MATCH_FLAG_COL, ColValues.MATCH_TYPE_NOT_A_MATCH));
            negPairs = negPairs.drop(ColName.MATCH_FLAG_COL,
                    ColName.COL_PREFIX + ColName.MATCH_FLAG_COL,
                    ColName.CLUSTER_COLUMN,
                    ColName.COL_PREFIX + ColName.CLUSTER_COLUMN);

            LOG.warn("Negative pairs count " + negPairs.count());
        }
        if (negPairs != null) {
            negPairs = negPairs.cache();
        }
        return new NegativePairs<>(negPairs);
    }

    public ZFrame<D,R,C> getTraining() {
        DSUtil<S, D, R, C> dsUtil = context.getDSUtil();;
        PipeUtilBase<S, D, R, C> pipeUtilBase = context.getPipeUtil();
        IModelHelper<D, R, C> modelHelper = context.getModelHelper();
        return dsUtil.getTraining(pipeUtilBase, arguments, modelHelper);
    }
}
