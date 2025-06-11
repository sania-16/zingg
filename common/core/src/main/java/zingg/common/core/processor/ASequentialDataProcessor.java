package zingg.common.core.processor;

import zingg.common.client.ZFrame;
import zingg.common.core.data.Data;
import zingg.common.core.queue.DataQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class ASequentialDataProcessor<S, D, R, C, T> implements IDataProcessor<S, D, R, C, T> {

    private final List<DataQueue<D, R, C>> queues;
    private final AZFrameProcessor<D, R, C> zFrameProcessor;

    public ASequentialDataProcessor(AZFrameProcessor<D, R, C> zFrameProcessor) {
        this.queues = new ArrayList<>();
        this.zFrameProcessor = zFrameProcessor;
    }

    @Override
    public Data<D, R, C> process(Data<D, R, C> data) {
        List<ZFrame<D, R, C>> zFrames = data.getzFrames();
        List<ZFrame<D, R, C>> processedZFrames = new ArrayList<>();
        for (ZFrame<D, R, C> zFrame : zFrames) {
            processedZFrames.add(zFrameProcessor.processZFrame(zFrame));
        }
        Data<D, R, C> processedOutput = getProcessedOutput(processedZFrames);
        publishToExternalQueues(processedOutput);
        return processedOutput;
    }


    public abstract Data<D, R, C> getProcessedOutput(List<ZFrame<D, R, C>> zFrames);

    public void addExternalProcessingQueue(DataQueue<D, R, C> dataQueue) {
        queues.add(dataQueue);
    }

    private void publishToExternalQueues(Data<D, R, C> processedOutput) {
        for (DataQueue<D, R, C> dataQueue : queues) {
            dataQueue.addToQueue(processedOutput);
        }
    }
}
