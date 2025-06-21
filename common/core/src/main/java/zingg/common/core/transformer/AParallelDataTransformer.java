package zingg.common.core.transformer;

import zingg.common.core.data.Data;
import zingg.common.core.queue.DataQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class AParallelDataTransformer<S, D, R, C, T> implements IDataTransformer<S, D, R, C, T> {
    private final List<DataQueue<D, R, C>> queues;

    public AParallelDataTransformer() {
        this.queues = new ArrayList<>();
    }

    @Override
    public Data<D, R, C> transform(Data<D, R, C> data) throws Exception {
        Data<D, R, C> processedOutput = transformData(data);
        publishToExternalQueues(processedOutput);
        return processedOutput;
    }

    public abstract Data<D, R, C> transformData(Data<D, R, C> data) throws Exception;

    private void publishToExternalQueues(Data<D, R, C> processedOutput) {
        for (DataQueue<D, R, C> dataQueue : queues) {
            dataQueue.addToQueue(processedOutput);
        }
    }

    public void addExternalProcessingQueue(DataQueue<D, R, C> dataQueue) {
        queues.add(dataQueue);
    }
}
