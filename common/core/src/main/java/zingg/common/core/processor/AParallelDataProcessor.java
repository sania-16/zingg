package zingg.common.core.processor;

import zingg.common.core.data.Data;
import zingg.common.core.queue.DataQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class AParallelDataProcessor<S, D, R, C, T> implements IDataProcessor<S, D, R, C, T> {
    private final List<DataQueue<D, R, C>> queues;

    public AParallelDataProcessor() {
        this.queues = new ArrayList<>();
    }

    @Override
    public Data<D, R, C> process(Data<D, R, C> data) {
        Data<D, R, C> processedOutput = processData(data);
        publishToExternalQueues(processedOutput);
        return processedOutput;
    }

    public abstract Data<D, R, C> processData(Data<D, R, C> data);

    private void publishToExternalQueues(Data<D, R, C> processedOutput) {
        for (DataQueue<D, R, C> dataQueue : queues) {
            dataQueue.addToQueue(processedOutput);
        }
    }

    public void addExternalProcessingQueue(DataQueue<D, R, C> dataQueue) {
        queues.add(dataQueue);
    }
}
