package zingg.spark.core.transformer.map;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataType;
import zingg.common.client.IArguments;
import zingg.common.client.ZinggClientException;
import zingg.common.client.cols.PredictionColsSelector;
import zingg.common.core.context.Context;
import zingg.common.core.filter.PredictionFilter;
import zingg.common.core.match.output.GraphMatchOutputBuilder;
import zingg.common.core.model.Model;
import zingg.common.core.transformer.impl.PredictionFilterTransformer;
import zingg.common.core.transformer.map.TransformerMap;
import zingg.spark.core.transformer.SparkBlockTransformer;
import zingg.spark.core.transformer.SparkHashTransformer;
import zingg.spark.core.transformer.SparkOutputTransformer;
import zingg.spark.core.transformer.SparkPredictionTransformer;

public class SparkMatcherTransformerMap<S, D, R, C, T, V> extends TransformerMap<SparkSession, Dataset<Row>, Row, Column, DataType, V> {

    public SparkMatcherTransformerMap(Context<SparkSession, Dataset<Row>, Row, Column, DataType> context, IArguments arguments) {
        super(context, arguments);
    }

    @Override
    public void init() throws ZinggClientException {
        transformers.put("hashTransformer", (V) new SparkHashTransformer(context, arguments));
        transformers.put("blockTransformer", (V) new SparkBlockTransformer<>(context));
        SparkPredictionTransformer predictionTransformer = new SparkPredictionTransformer(context, arguments);
        Model model = context.getModelUtil().loadModel(false, arguments, context.getModelHelper());
        model.register();
        predictionTransformer.setModel(model);
        transformers.put("predictionTransformer", (V) new SparkPredictionTransformer(context, arguments));
        transformers.put("predictionFilterTransformer", (V) new PredictionFilterTransformer(new PredictionFilter(), new PredictionColsSelector()));
        transformers.put("outputTransformer", (V) new SparkOutputTransformer(new GraphMatchOutputBuilder(context.getGraphUtil(), context.getDSUtil(), arguments)));
    }
}
