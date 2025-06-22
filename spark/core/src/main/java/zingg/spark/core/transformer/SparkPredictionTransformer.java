package zingg.spark.core.transformer;

import zingg.common.client.IArguments;
import zingg.common.core.context.Context;
import zingg.common.core.transformer.impl.PredictionTransformer;

public class SparkPredictionTransformer extends PredictionTransformer {
    public SparkPredictionTransformer(Context context, IArguments arguments) {
        super(context, arguments);
    }
}
