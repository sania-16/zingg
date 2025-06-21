package zingg.common.core.transformer.strategy;

import zingg.common.core.context.Context;
import zingg.common.core.data.Data;
import zingg.common.core.transformer.strategy.impl.SourceInsensitiveBlockingStrategy;
import zingg.common.core.transformer.strategy.impl.SourceSensitiveBlockingStrategy;

public class BlockingStrategyProvider<S, D, R, C, T> {

    public static <S, D, R, C, T> IBlockCreationStrategy<D, R, C> getBlockingStrategy(Context<S, D, R, C, T> context, Data<D, R, C> data) {
        if (data.getNumberOfZFrames() == 1) {
            return new SourceInsensitiveBlockingStrategy<>(context);
        }
        return new SourceSensitiveBlockingStrategy<>(context);
    }
}
