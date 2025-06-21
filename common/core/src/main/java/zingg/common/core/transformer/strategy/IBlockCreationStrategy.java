package zingg.common.core.transformer.strategy;

import zingg.common.client.ZFrame;
import zingg.common.core.data.Data;

public interface IBlockCreationStrategy<D, R, C> {
    ZFrame<D, R, C> createBlocks(Data<D, R, C> data) throws Exception;
}
