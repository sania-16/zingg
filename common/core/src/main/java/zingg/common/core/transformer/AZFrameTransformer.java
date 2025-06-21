package zingg.common.core.transformer;

import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.core.queue.ZFrameQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class AZFrameTransformer<D, R, C> implements IZFrameTransformer<D, R, C> {
    private final List<ZFrameQueue<D, R, C>> queues;

    public AZFrameTransformer() {
        this.queues = new ArrayList<>();
    }

    @Override
    public ZFrame<D, R, C> transform(ZFrame<D, R, C> data) throws ZinggClientException, Exception {
        ZFrame<D, R, C> processedOutput = transformZFrame(data);
        // get processed output
        publishToExternalQueues(processedOutput);
        return processedOutput;
    }

    public abstract ZFrame<D, R, C> transformZFrame(ZFrame<D, R, C> data) throws ZinggClientException, Exception;

    private void publishToExternalQueues(ZFrame<D, R, C> processedOutput) {
        for (ZFrameQueue<D, R, C> dataQueue : queues) {
            dataQueue.addToQueue(processedOutput);
        }
    }

    public void addExternalProcessingQueue(ZFrameQueue<D, R, C> queue) {
        queues.add(queue);
    }
}
