package zingg.common.core.transformer.impl;

import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.client.cols.ZidAndFieldDefSelector;
import zingg.common.core.transformer.AZFrameTransformer;

public class FieldSelector<S, D, R, C, T> extends AZFrameTransformer<D, R, C> {

    private final IArguments arguments;

    public FieldSelector(IArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public ZFrame<D, R, C> transformZFrame(ZFrame<D, R, C> zFrame) {
        ZidAndFieldDefSelector zidAndFieldDefSelector = new ZidAndFieldDefSelector(arguments.getFieldDefinition());
        String[] cols = zidAndFieldDefSelector.getCols();
        return zFrame.select(cols);
    }

}
