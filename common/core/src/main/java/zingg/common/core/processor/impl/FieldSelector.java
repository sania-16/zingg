package zingg.common.core.processor.impl;

import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.client.cols.ZidAndFieldDefSelector;
import zingg.common.core.data.AData;
import zingg.common.core.data.impl.CompressedData;
import zingg.common.core.processor.ASequentialProcessor;
import zingg.common.core.processor.enums.ProcessingType;

import java.util.List;

public class FieldSelector<S, D, R, C, T> extends ASequentialProcessor<S, D, R, C, T> {

    private final IArguments arguments;

    public FieldSelector(IArguments arguments) {
        super(ProcessingType.FIELD_SELECTOR);
        this.arguments = arguments;
    }

    @Override
    public ZFrame<D, R, C> processZFrame(ZFrame<D, R, C> zFrame) {
        ZidAndFieldDefSelector zidAndFieldDefSelector = new ZidAndFieldDefSelector(arguments.getFieldDefinition());
        String[] cols = zidAndFieldDefSelector.getCols();
        return zFrame.select(cols);
    }

    @Override
    public AData<D, R, C> getProcessedOutput(List<ZFrame<D, R, C>> zFrames) {
        return new CompressedData<>(zFrames);
    }
}
