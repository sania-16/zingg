package zingg.common.core.data.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.data.AData;

import java.util.List;

public class CompressedData<D, R, C> extends AData<D, R, C> {
    public CompressedData(List<ZFrame<D, R, C>> compressedZFrames) {
        super(compressedZFrames);
    }
}
