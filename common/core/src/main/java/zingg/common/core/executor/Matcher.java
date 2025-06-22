package zingg.common.core.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zingg.common.client.ClientOptions;
import zingg.common.client.IZArgs;
import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.client.cols.ZidAndFieldDefSelector;
import zingg.common.client.options.ZinggOptions;
import zingg.common.core.block.InputDataGetter;
import zingg.common.core.data.Data;
import zingg.common.core.match.data.IDataGetter;
import zingg.common.core.pairs.IPairBuilder;
import zingg.common.core.preprocess.IPreprocessors;
import zingg.common.core.transformer.map.TransformerMap;
import zingg.common.core.transformer.pipeline.MatcherTransformerPipeline;
import zingg.common.core.util.Analytics;
import zingg.common.core.util.Metric;

public abstract class Matcher<S,D,R,C,T> extends ZinggBase<S,D,R,C,T> implements IPreprocessors<S,D,R,C,T> {

	private static final long serialVersionUID = 1L;
	protected static String name = "zingg.Matcher";
	public static final Log LOG = LogFactory.getLog(Matcher.class);   
	ZFrame<D, R, C> output = null;
	boolean toWrite = true;
	protected IDataGetter<S, D, R, C> dataGetter;
	protected IPairBuilder<S, D, R, C> iPairBuilder;
	private TransformerMap transformerMap;

	
	public Matcher() {
        setZinggOption(ZinggOptions.MATCH);
    }

	@Override 
	public void init(IZArgs args, S session, ClientOptions c) throws ZinggClientException{
		super.init(args, session, c);

	}

	public ZFrame<D, R, C> getOutput() {
		return output;
	}

	public void setOutput(ZFrame<D, R, C> output) {
		this.output = output;
	}

	public boolean isToWrite() {
		return toWrite;
	}

	public void setToWrite(boolean toWrite) {
		this.toWrite = toWrite;
	}

	public ZFrame<D,R,C> getTestData() throws ZinggClientException{
		return getDataGetter().getData(args, getPipeUtil());
	}


	public IDataGetter<S,D,R,C> getDataGetter(){
		if (dataGetter == null){
			this.dataGetter = new InputDataGetter<S,D,R,C>(getPipeUtil());
		}
		return dataGetter;
	}

	public ZFrame<D, R, C> getFieldDefColumnsDS(ZFrame<D, R, C> testDataOriginal) {
		ZidAndFieldDefSelector zidAndFieldDefSelector = new ZidAndFieldDefSelector(args.getFieldDefinition());
		return testDataOriginal.select(zidAndFieldDefSelector.getCols());
	}


	@Override
    public void execute() throws ZinggClientException {
        try {
			// read input, filter, remove self joins
			ZFrame<D,R,C>  testDataOriginal = getTestData();
			testDataOriginal =  getFieldDefColumnsDS(testDataOriginal).cache();
			ZFrame<D,R,C>  testData = preprocess(testDataOriginal);
			long count = testData.count();
			LOG.info("Read " + count);
			Analytics.track(Metric.DATA_COUNT, count, args.getCollectMetrics());

			MatcherTransformerPipeline matcherTransformerPipeline = new MatcherTransformerPipeline(transformerMap);
			matcherTransformerPipeline.setTestDataOriginal(testDataOriginal);
			Data<D, R, C> pipelineInputData = getPipelineInputData(testData);
			ZFrame<D, R, C> identityGraph = matcherTransformerPipeline.executePipeline(pipelineInputData);

			setOutput(identityGraph);
			if (args.getOutput() != null && toWrite) {
				getPipeUtil().write(identityGraph, args.getOutput());
			}
			
		} catch (Exception e) {
			if (LOG.isDebugEnabled()) e.printStackTrace();
			throw new ZinggClientException(e.getMessage());
		}
    }

	public void setTransformerMap(TransformerMap transformerMap) {
		this.transformerMap = transformerMap;
	}

	private Data<D, R, C> getPipelineInputData(ZFrame<D, R, C> testData) {
		return new Data<>(testData);
	}

}
