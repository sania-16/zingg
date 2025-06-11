package zingg.common.core.processor;

import zingg.common.client.ZFrame;
import zingg.common.core.queue.ZFrameQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class AZFrameProcessor<D, R, C> implements IZFrameProcessor<D, R, C> {
    private final List<ZFrameQueue<D, R, C>> queues;

    public AZFrameProcessor() {
        this.queues = new ArrayList<>();
    }

    @Override
    public ZFrame<D, R, C> process(ZFrame<D, R, C> data) {
        ZFrame<D, R, C> processedOutput = processZFrame(data);
        // get processed output
        publishToExternalQueues(processedOutput);
        return processedOutput;
    }

    public abstract ZFrame<D, R, C> processZFrame(ZFrame<D, R, C> data);

    private void publishToExternalQueues(ZFrame<D, R, C> processedOutput) {
        for (ZFrameQueue<D, R, C> dataQueue : queues) {
            dataQueue.addToQueue(processedOutput);
        }
    }

    public void addExternalProcessingQueue(ZFrameQueue<D, R, C> queue) {
        queues.add(queue);
    }
}
