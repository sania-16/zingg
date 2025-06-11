package zingg.common.core.processor;

import zingg.common.client.ZinggClientException;
import zingg.common.core.data.Data;

public interface IDataProcessor<S, D, R, C, T> {
    Data<D, R, C> process(Data<D, R, C> data) throws Exception, ZinggClientException;
}
