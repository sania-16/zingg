package zingg.spark.core.transformer;

import zingg.common.core.match.output.IMatchOutputBuilder;
import zingg.common.core.transformer.impl.OutputTransformer;

public class SparkOutputTransformer extends OutputTransformer {
    public SparkOutputTransformer(IMatchOutputBuilder matchOutputBuilder) {
        super(matchOutputBuilder);
    }
}
