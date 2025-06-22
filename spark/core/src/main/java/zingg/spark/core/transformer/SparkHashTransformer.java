package zingg.spark.core.transformer;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataType;
import zingg.common.client.IArguments;
import zingg.common.core.context.Context;
import zingg.common.core.transformer.impl.HashTransformer;
import zingg.spark.core.preprocess.ISparkPreprocMapSupplier;

public class SparkHashTransformer extends HashTransformer<SparkSession, Dataset<Row>, Row, Column, DataType> implements ISparkPreprocMapSupplier {
    public SparkHashTransformer(Context context, IArguments arguments) {
        super(context, arguments);
    }
}
