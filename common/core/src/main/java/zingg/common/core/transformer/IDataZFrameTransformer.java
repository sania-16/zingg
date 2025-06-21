package zingg.common.core.transformer;

import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.core.data.Data;

public interface IDataZFrameTransformer<D, R, C> {
    ZFrame<D, R, C> transform(Data<D, R, C> data) throws Exception, ZinggClientException;
}
