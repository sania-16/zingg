package zingg.common.core.transformer.strategy.impl;

import zingg.common.client.ZFrame;
import zingg.common.client.util.ColName;
import zingg.common.core.context.Context;
import zingg.common.core.data.Data;
import zingg.common.core.transformer.ZFrameTypes;
import zingg.common.core.transformer.strategy.IBlockCreationStrategy;

public class SourceInsensitiveBlockingStrategy<S, D, R, C, T> implements IBlockCreationStrategy<D, R, C> {

    private final Context<S, D, R, C, T> context;

    public SourceInsensitiveBlockingStrategy(Context<S, D, R, C, T> context) {
        this.context = context;
    }

    @Override
    public ZFrame<D, R, C> createBlocks(Data<D, R, C> data) throws Exception {
        ZFrame<D, R, C> hashedZFrame = data.getZFrameByName(ZFrameTypes.HASHED.name());
        ZFrame<D, R, C> pairs =  context.getDSUtil().joinWithItself(hashedZFrame, ColName.HASH_COL, true);
        return pairs.filter(pairs.gt(ColName.ID_COL));
    }
}
