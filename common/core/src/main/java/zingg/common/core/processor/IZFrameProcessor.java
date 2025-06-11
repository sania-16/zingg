package zingg.common.core.processor;

import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;

public interface IZFrameProcessor<D, R, C> {
    ZFrame<D, R, C> process(ZFrame<D, R, C> data) throws Exception, ZinggClientException;
}
