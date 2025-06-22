package zingg.spark.core.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataType;

import zingg.common.client.ClientOptions;
import zingg.common.client.IArguments;
import zingg.common.client.IZArgs;
import zingg.common.client.ZinggClientException;
import zingg.common.client.options.ZinggOptions;
import zingg.common.core.context.Context;
import zingg.spark.core.context.ZinggSparkContext;
import zingg.common.core.executor.Matcher;
import org.apache.spark.sql.SparkSession;

import zingg.spark.core.preprocess.ISparkPreprocMapSupplier;
import zingg.spark.core.transformer.map.SparkMatcherTransformerMap;

/**
 * Spark specific implementation of Matcher
 * 
 *
 */
public class SparkMatcher extends Matcher<SparkSession,Dataset<Row>,Row,Column,DataType> implements ISparkPreprocMapSupplier {


	private static final long serialVersionUID = 1L;
	public static String name = "zingg.spark.core.executor.SparkMatcher";
	public static final Log LOG = LogFactory.getLog(SparkMatcher.class);    

    public SparkMatcher() {
        this(new ZinggSparkContext());
    }

    public SparkMatcher(ZinggSparkContext sparkContext) {
        setZinggOption(ZinggOptions.MATCH);
		setContext(sparkContext);
    }

    @Override
    public void init(IZArgs args, SparkSession s, ClientOptions options)  throws ZinggClientException {
        super.init(args,s,options);
        getContext().init(s);
		setTransformerMap(new SparkMatcherTransformerMap((Context<SparkSession, Dataset<Row>, Row, Column, DataType>) getContext(), (IArguments) args));
    }

}
