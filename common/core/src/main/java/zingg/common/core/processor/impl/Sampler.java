package zingg.common.core.processor.impl;

import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.core.processor.AZFrameProcessor;

public class Sampler<S, D, R, C, T> extends AZFrameProcessor<D, R, C> {

    private final IArguments arguments;

    public Sampler(IArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public ZFrame<D, R, C> processZFrame(ZFrame<D, R, C> zFrame) {
        return zFrame.sample(false, arguments.getLabelDataSampleSize());
    }

}
