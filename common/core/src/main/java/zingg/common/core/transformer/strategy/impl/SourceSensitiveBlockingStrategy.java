package zingg.common.core.transformer.strategy.impl;

import zingg.common.client.ZFrame;
import zingg.common.client.util.ColName;
import zingg.common.core.context.Context;
import zingg.common.core.data.Data;
import zingg.common.core.transformer.strategy.IBlockCreationStrategy;

public class SourceSensitiveBlockingStrategy<S, D, R, C, T> implements IBlockCreationStrategy<D, R, C> {

    private final Context<S, D, R, C, T> context;

    public SourceSensitiveBlockingStrategy(Context<S, D, R, C, T> context) {
        this.context = context;
    }

    @Override
    public ZFrame<D, R, C> createBlocks(Data<D, R, C> data) throws Exception {
        ZFrame<D, R, C> hashedZFrameSource1 = data.getzFrames().get(0);
        ZFrame<D, R, C> hashedZFrameSource2 = data.getzFrames().get(1);
        return context.getDSUtil().join(hashedZFrameSource1, hashedZFrameSource2, ColName.HASH_COL, true);
    }
}
