package zingg.common.core.preprocess.stopwords;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zingg.common.client.FieldDefinition;
import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.client.util.ColName;
import zingg.common.client.util.PipeUtilBase;
import zingg.common.core.context.IContext;
import zingg.common.core.preprocess.IPreprocessor;

public abstract class StopWordsRemover<S,D,R,C,T> implements IPreprocessor<S,D,R,C,T>{

	private static final long serialVersionUID = 1L;
	protected static String name = "zingg.preprocess.stopwords.StopWordsRemover";
	public static final Log LOG = LogFactory.getLog(StopWordsRemover.class);
	protected static final int COLUMN_INDEX_DEFAULT = 0;
	
	protected IContext<S,D,R,C,T> context;
    protected FieldDefinition fd;

	public StopWordsRemover(IContext<S, D, R, C, T> context) {
		super();
		this.context = context;
	}

    @Override
    public boolean isApplicable(FieldDefinition fd){
		if (!(fd.getStopWords() == null || fd.getStopWords() == "")) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public ZFrame<D,R,C> preprocess(ZFrame<D,R,C> df) throws ZinggClientException{
        ZFrame<D, R, C> stopWords = getStopWords(getFieldDefinition());
		String stopWordColumn = getStopWordColumnName(stopWords);
		List<String> wordList = getWordList(stopWords,stopWordColumn);
		String pattern = getPattern(wordList);
		df = removeStopWordsFromDF(df, fd.getFieldName(), pattern);
        return df;
    }

	protected ZFrame<D,R,C> getStopWords(FieldDefinition def) throws ZinggClientException {
		PipeUtilBase<S,D,R,C> pipeUtil = getContext().getPipeUtil();
		ZFrame<D,R,C> stopWords = pipeUtil.read(false, false, getContext().getModelHelper().getStopWordsPipe(def.getStopWords()));
		return stopWords;
	}

	/**
	 * Return the 0th column name if the column ColName.COL_WORD is not in the list of the columns of the stopwords DF
	 * @return
	 */
	protected String getStopWordColumnName(ZFrame<D,R,C> stopWords) {
		String[] fieldNames = stopWords.fieldNames();
		if (!Arrays.asList(fieldNames).contains(ColName.COL_WORD)) {
			return stopWords.columns()[getColumnIndexDefault()];
		} else {
			return ColName.COL_WORD;
		}
	}
	
	protected List<String> getWordList(ZFrame<D,R,C> stopWords, String stopWordColumn) {
		return stopWords.select(stopWordColumn).collectFirstColumn();
	}
	
	/**
	 * Regex to remove the stop words
	 * @param wordList
	 * @return
	 */
	protected String getPattern(List<String> wordList) {
		String pattern = wordList.stream().collect(Collectors.joining("|", "\\b(", ")\\b\\s?"));
		String lowerCasePattern = pattern.toLowerCase();
		return lowerCasePattern;
	}
    
	// implementation specific as may require UDF
	protected abstract ZFrame<D,R,C> removeStopWordsFromDF(ZFrame<D,R,C> ds,String fieldName, String pattern);
	
	public IContext<S, D, R, C, T> getContext() {
		return context;
	}

    @Override
	public void setContext(IContext<S, D, R, C, T> context) {
		this.context = context;
	}

	public static int getColumnIndexDefault() {
		return COLUMN_INDEX_DEFAULT;
	}

    @Override
    public void setFieldDefinition(FieldDefinition fd){
        this.fd = fd;
    }

    @Override
    public FieldDefinition getFieldDefinition(){
        return fd;
    }

    /*
    public ZFrame<D, R, C> preprocessForStopWords(ZFrame<D, R, C> ds) throws ZinggClientException {
		for (FieldDefinition def : getArgs().getFieldDefinition()) {
			if (!(def.getStopWords() == null || def.getStopWords() == "")) {
				ZFrame<D, R, C> stopWords = getStopWords(def);
				String stopWordColumn = getStopWordColumnName(stopWords);
				List<String> wordList = getWordList(stopWords,stopWordColumn);
				String pattern = getPattern(wordList);
				ds = removeStopWordsFromDF(ds, def.getFieldName(), pattern);
			}
		}
		return ds;
	}
     */
	
}