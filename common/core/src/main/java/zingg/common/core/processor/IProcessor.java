package zingg.common.core.processor;

import zingg.common.client.ZinggClientException;
import zingg.common.core.data.AData;
import zingg.common.core.processor.enums.ProcessingType;

public interface IProcessor<S, D, R, C, T> {
    AData<D, R, C> process(AData<D, R, C> data) throws Exception, ZinggClientException;
    ProcessingType getProcessingType();
    void sendToExternalQueueIfRequired(AData<D, R, C> processedOutput);
}
