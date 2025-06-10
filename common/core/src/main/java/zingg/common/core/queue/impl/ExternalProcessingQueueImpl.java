package zingg.common.core.queue.impl;

import zingg.common.client.ZinggClientException;
import zingg.common.core.data.AData;
import zingg.common.core.processor.IProcessor;
import zingg.common.core.queue.ExternalProcessingQueue;

import java.util.ArrayList;
import java.util.List;

public class ExternalProcessingQueueImpl<S, D, R, C, T> implements ExternalProcessingQueue<D, R, C> {

    private final List<AData<D, R, C>> queue;
    private static ExternalProcessingQueueImpl externalProcessingQueueImpl = null;

    /*
        These are the subscribers who will do side actions on AData
        like debugging, explaining, sending data to stat manager etc
        Subscriber can poll queue at their own pace and time and hence
        not blocking the original code flow
     */
    private final List<IProcessor<S, D, R, C, T>> subscribers;

    private ExternalProcessingQueueImpl() {
        queue = new ArrayList<>();
        subscribers = new ArrayList<>();
    }

    @Override
    public void addToQueue(AData<D, R, C> data) {
        queue.add(data);
    }

    /**
     * Process the data from queue, and then remove it from queue
     * @throws ZinggClientException
     * @throws Exception
     */
    @Override
    public void process() throws ZinggClientException, Exception {
        for (AData<D, R, C> data : queue) {
            for (IProcessor<S, D, R, C, T> processor : subscribers) {
                processor.process(data);
                queue.remove(data);
            }
        }
    }


    public void addSubscriber(IProcessor<S, D, R, C, T> processor) {
        this.subscribers.add(processor);
    }

    public static ExternalProcessingQueueImpl getInstance() {
        if (externalProcessingQueueImpl == null) {
            externalProcessingQueueImpl = new ExternalProcessingQueueImpl();
        }
        return externalProcessingQueueImpl;
    }
}
