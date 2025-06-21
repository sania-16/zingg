package zingg.common.core.transformer;

import zingg.common.client.ZinggClientException;
import zingg.common.core.data.Data;

public interface IDataTransformer<S, D, R, C, T> {
    Data<D, R, C> transform(Data<D, R, C> data) throws Exception, ZinggClientException;
}
