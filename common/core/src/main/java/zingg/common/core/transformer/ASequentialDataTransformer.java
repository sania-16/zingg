package zingg.common.core.transformer;

import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.core.data.Data;
import zingg.common.core.queue.DataQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class ASequentialDataTransformer<S, D, R, C, T> implements IDataTransformer<S, D, R, C, T> {

    private final List<DataQueue<D, R, C>> queues;

    public ASequentialDataTransformer() {
        this.queues = new ArrayList<>();
    }

    @Override
    public Data<D, R, C> transform(Data<D, R, C> data) throws ZinggClientException, Exception {
        List<ZFrame<D, R, C>> zFrames = data.getzFrames();
        List<ZFrame<D, R, C>> processedZFrames = new ArrayList<>();
        for (ZFrame<D, R, C> zFrame : zFrames) {
            processedZFrames.add(processZFrame(zFrame));
        }
        Data<D, R, C> processedOutput = new Data<>(processedZFrames);
        publishToExternalQueues(processedOutput);
        return processedOutput;
    }

    protected abstract ZFrame<D, R, C> processZFrame(ZFrame<D, R, C> zFrame) throws ZinggClientException, Exception;

    public void addExternalProcessingQueue(DataQueue<D, R, C> dataQueue) {
        queues.add(dataQueue);
    }

    private void publishToExternalQueues(Data<D, R, C> processedOutput) {
        for (DataQueue<D, R, C> dataQueue : queues) {
            dataQueue.addToQueue(processedOutput);
        }
    }
}
