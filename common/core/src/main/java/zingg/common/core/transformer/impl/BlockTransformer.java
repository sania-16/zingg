package zingg.common.core.transformer.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.context.Context;
import zingg.common.core.data.Data;
import zingg.common.core.transformer.IDataZFrameTransformer;
import zingg.common.core.transformer.strategy.BlockingStrategyProvider;
import zingg.common.core.transformer.strategy.IBlockCreationStrategy;

public abstract class BlockTransformer<S, D, R, C, T> implements IDataZFrameTransformer<D, R, C> {

    private final Context<S, D, R, C, T> context;

    public BlockTransformer(Context<S, D, R, C, T> context) {
        this.context = context;
    }

    /**
     * Perform blocking across multiple zFrames in data
     * @param data
     * @return blocks data
     */
    @Override
    public ZFrame<D, R, C> transform(Data<D, R, C> data) throws Exception {
        IBlockCreationStrategy<D, R, C> blockCreationStrategy = BlockingStrategyProvider.getBlockingStrategy(context, data);
        return blockCreationStrategy.createBlocks(data);
    }
}
