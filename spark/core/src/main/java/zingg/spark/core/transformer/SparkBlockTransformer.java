package zingg.spark.core.transformer;

import zingg.common.core.context.Context;
import zingg.common.core.transformer.impl.BlockTransformer;

public class SparkBlockTransformer<S, D, R, C, T> extends BlockTransformer<S, D, R, C, T> {
    public SparkBlockTransformer(Context<S, D, R, C, T> context) {
        super(context);
    }
}
