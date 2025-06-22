package zingg.common.core.transformer.pipeline;

import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.core.data.Data;

public interface TransformerPipeline<D, R, C> {
    ZFrame<D, R, C> executePipeline(Data<D, R, C> data) throws ZinggClientException, Exception;
}
