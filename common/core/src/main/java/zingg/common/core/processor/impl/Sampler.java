package zingg.common.core.processor.impl;

import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.core.data.AData;
import zingg.common.core.data.impl.GenericIntermediateData;
import zingg.common.core.processor.ASequentialProcessor;

import java.util.List;

public class Sampler<S, D, R, C, T> extends ASequentialProcessor<S, D, R, C, T> {

    private final IArguments arguments;

    public Sampler(IArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public ZFrame<D, R, C> processZFrame(ZFrame<D, R, C> zFrame) {
        return zFrame.sample(false, arguments.getLabelDataSampleSize());
    }

    @Override
    public AData<D, R, C> getProcessedOutput(List<ZFrame<D, R, C>> zFrames) {
        return new GenericIntermediateData<>(zFrames);
    }
}
