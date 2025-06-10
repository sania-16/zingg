package zingg.common.core.processor.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.client.util.*;
import zingg.common.core.context.IContext;
import zingg.common.core.data.AData;
import zingg.common.core.data.impl.GenericIntermediateData;
import zingg.common.core.data.impl.PositivePairs;
import zingg.common.core.preprocess.IPreprocessors;
import zingg.common.core.processor.IProcessor;
import zingg.common.core.processor.enums.ProcessingType;

public abstract class PositivePairGenerator<S, D, R, C, T> implements IProcessor<S, D, R, C, T>, IPreprocessors<S, D, R, C, T> {
    private static final Log LOG = LogFactory.getLog(PositivePairGenerator.class);
    private final IArguments arguments;
    private final IContext<S, D, R, C, T> context;

    public PositivePairGenerator(IArguments arguments, IContext<S,D,R,C,T> context) {
        this.arguments = arguments;
        this.context = context;
    }

    @Override
    public ProcessingType getProcessingType() {
        return ProcessingType.POSITIVE_PAIR_GENERATOR;
    }

    @Override
    public AData<D, R, C> process(AData<D, R, C> data) throws ZinggClientException, Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Total count is " + data.count());
            LOG.debug("Label data sample size is " + arguments.getLabelDataSampleSize());
        }

        ZFrame<D,R,C> trFile = getTraining();
        ZFrame<D,R,C> posPairs = null;
        if (trFile != null) {
            trFile = preprocess(trFile);
            ZFrame<D,R,C> trPairs = context.getDSUtil().joinWithItself(trFile, ColName.CLUSTER_COLUMN, true);
            posPairs = trPairs.filter(trPairs.equalTo(ColName.MATCH_FLAG_COL, ColValues.MATCH_TYPE_MATCH));
            posPairs = posPairs.drop(ColName.MATCH_FLAG_COL,
                    ColName.COL_PREFIX + ColName.MATCH_FLAG_COL,
                    ColName.CLUSTER_COLUMN,
                    ColName.COL_PREFIX + ColName.CLUSTER_COLUMN);

        }

        if (posPairs == null || posPairs.count() <= 5) {
            ZFrame<D,R,C> posSamples = getPositiveSamples(data);
            //ZFrame<D,R,C> posSamples = preprocess(posSamplesOriginal);
            //posSamples.printSchema();
            if (posPairs != null) {
                //posPairs.printSchema();
                posPairs = posPairs.union(posSamples);
            }
            else {
                posPairs = posSamples;
            }
        }

        return new PositivePairs<>(posPairs);
    }

    public ZFrame<D,R,C> getTraining() {
        DSUtil<S, D, R, C> dsUtil = context.getDSUtil();;
        PipeUtilBase<S, D, R, C> pipeUtilBase = context.getPipeUtil();
        IModelHelper<D, R, C> modelHelper = context.getModelHelper();
        return dsUtil.getTraining(pipeUtilBase, arguments, modelHelper);
    }

    public ZFrame<D,R,C> getPositiveSamples(AData<D,R,C> data) throws Exception, ZinggClientException {
        if (LOG.isDebugEnabled()) {
            long count = data.count();
            LOG.debug("Total count is " + count);
            LOG.debug("Label data sample size is " + arguments.getLabelDataSampleSize());
        }
        Sampler<S, D, R, C, T> sampler = new Sampler<>(arguments);
        GenericIntermediateData<D, R, C> positiveSamples = (GenericIntermediateData<D, R, C>) sampler.process(data);
//        ZFrame<D,R,C> posSample = data.sample(false, arguments.getLabelDataSampleSize());
        //select only those columns which are mentioned in the field definitions
        FieldSelector<S, D, R, C, T> fieldSelector = new FieldSelector<>(arguments);
        positiveSamples = (GenericIntermediateData<D, R, C>) fieldSelector.process(positiveSamples);
//        posSample = getFieldDefColumnsDS(posSample);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Sampled " + positiveSamples.count());
        }
        positiveSamples = (GenericIntermediateData<D, R, C>) positiveSamples.cache();
        positiveSamples = (GenericIntermediateData<D, R, C>) process(positiveSamples);


        ZFrame<D,R,C> posPairs = context.getDSUtil().joinWithItself(positiveSamples.getzFrames().get(0), ColName.ID_COL, false);

        LOG.info("Created positive sample pairs ");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Pos Sample pairs count " + posPairs.count());
        }
        return posPairs;
    }
}
