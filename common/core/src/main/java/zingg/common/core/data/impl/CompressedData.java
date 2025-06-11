package zingg.common.core.data.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.data.Data;

import java.util.List;

public class CompressedData<D, R, C> extends Data<D, R, C> {
    public CompressedData(List<ZFrame<D, R, C>> compressedZFrames) {
        super(compressedZFrames);
    }
}
