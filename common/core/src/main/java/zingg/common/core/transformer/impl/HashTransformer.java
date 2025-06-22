package zingg.common.core.transformer.impl;

import zingg.common.client.IArguments;
import zingg.common.client.IZArgs;
import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.client.cols.ZidAndFieldDefSelector;
import zingg.common.client.util.ColName;
import zingg.common.core.block.Canopy;
import zingg.common.core.block.Tree;
import zingg.common.core.context.Context;
import zingg.common.core.context.IContext;
import zingg.common.core.preprocess.IPreprocessors;
import zingg.common.core.transformer.ASequentialDataTransformer;

public abstract class HashTransformer<S, D, R, C, T> extends ASequentialDataTransformer<S, D, R, C, T> implements IPreprocessors<S, D, R, C, T> {

    private Context<S, D, R, C, T> context;
    private IArguments arguments;

    public HashTransformer(Context<S, D, R, C, T> context, IArguments arguments) {
        this.context = context;
        this.arguments = arguments;
    }

    /***
     * This is responsible for creating blocked
     * @param positivePairs
     * @return blocked data
     */
    @Override
    public ZFrame<D, R, C> processZFrame(ZFrame<D, R, C> positivePairs) throws ZinggClientException, Exception {
        ZFrame<D, R, C> data = context.getPipeUtil().read(true, true, arguments.getData());
        ZFrame<D,R,C> sampleOrginal = data.sample(false, arguments.getLabelDataSampleSize()).repartition(arguments.getNumPartitions()).cache();
        sampleOrginal = getFieldDefColumnsDS(sampleOrginal);
        LOG.info("Preprocessing DS for stopWords");

        ZFrame<D,R,C> sample = preprocess(sampleOrginal);

        Tree<Canopy<R>> blockingTree = context.getBlockingTreeUtil().
                createBlockingTree(sample, positivePairs, 1, -1, arguments, context.getHashUtil().getHashFunctionList());
        ZFrame<D,R,C> blocked = context.getBlockingTreeUtil().getBlockHashes(sample, blockingTree);

        blocked = blocked.repartition(arguments.getNumPartitions(), blocked.col(ColName.HASH_COL)).cache();

        return blocked;
    }

    private ZFrame<D, R, C> getFieldDefColumnsDS(ZFrame<D, R, C> data) {
        ZidAndFieldDefSelector zidAndFieldDefSelector = new ZidAndFieldDefSelector(arguments.getFieldDefinition());
        String[] cols = zidAndFieldDefSelector.getCols();
        return data.select(cols);
    }

    @Override
    public void setContext(IContext context) {
        this.context = (Context<S, D, R, C, T>) context;
    }

    @Override
    public IContext getContext() {
        return this.context;
    }

    @Override
    public IZArgs getArgs() {
        return this.arguments;
    }

    @Override
    public void setArgs(IZArgs args) {
        this.arguments = (IArguments) args;
    }

}
