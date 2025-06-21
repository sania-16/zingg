package zingg.common.core.transformer;

import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;

public interface IZFrameTransformer<D, R, C> {
    ZFrame<D, R, C> transform(ZFrame<D, R, C> data) throws Exception, ZinggClientException;
}
