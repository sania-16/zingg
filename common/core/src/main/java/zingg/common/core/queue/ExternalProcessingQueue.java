package zingg.common.core.queue;

import zingg.common.client.ZinggClientException;
import zingg.common.core.data.AData;

public interface ExternalProcessingQueue<D, R, C> {
    void addToQueue(AData<D, R, C> data);
    void process() throws ZinggClientException, Exception;
}
