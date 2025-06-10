package zingg.common.core.processor;

import zingg.common.client.ZFrame;
import zingg.common.core.data.AData;
import zingg.common.core.processor.enums.ProcessingType;
import zingg.common.core.queue.ExternalProcessingQueue;
import zingg.common.core.queue.impl.ExternalProcessingQueueImpl;

import java.util.ArrayList;
import java.util.List;

public abstract class ASequentialProcessor<S, D, R, C, T> implements IProcessor<S, D, R, C, T> {
    private final ProcessingType processingType;

    public ASequentialProcessor(ProcessingType processingType) {
        this.processingType = processingType;
    }

    @Override
    public AData<D, R, C> process(AData<D, R, C> data) {
        List<ZFrame<D, R, C>> zFrames = data.getzFrames();
        List<ZFrame<D, R, C>> processedZFrames = new ArrayList<>();
        for (ZFrame<D, R, C> zFrame : zFrames) {
            processedZFrames.add(processZFrame(zFrame));
        }
        AData<D, R, C> processedOutput = getProcessedOutput(processedZFrames);
        sendToExternalQueueIfRequired(processedOutput);
        return processedOutput;
    }

    public abstract ZFrame<D, R, C> processZFrame(ZFrame<D, R, C> zFrame);

    public abstract AData<D, R, C> getProcessedOutput(List<ZFrame<D, R, C>> zFrames);

    @Override
    public ProcessingType getProcessingType() {
        return processingType;
    }

    @Override
    protected void sendToExternalQueueIfRequired(AData<D, R, C> processedOutput) {
        ExternalProcessingQueue<D, R, C> externalProcessingQueue = ExternalProcessingQueueImpl.getInstance();
        if (processedOutput.isDebuggable() || processedOutput.isExplainable()) {
            externalProcessingQueue.addToQueue(processedOutput);
        }
    }
}
