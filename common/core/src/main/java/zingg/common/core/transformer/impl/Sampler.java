package zingg.common.core.transformer.impl;

import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.core.transformer.AZFrameTransformer;

public class Sampler<S, D, R, C, T> extends AZFrameTransformer<D, R, C> {

    private final IArguments arguments;

    public Sampler(IArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public ZFrame<D, R, C> transformZFrame(ZFrame<D, R, C> zFrame) {
        return zFrame.sample(false, arguments.getLabelDataSampleSize());
    }

}
