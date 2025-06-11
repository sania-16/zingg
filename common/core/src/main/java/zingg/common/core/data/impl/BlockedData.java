package zingg.common.core.data.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.data.Data;

import java.util.List;

public class BlockedData<D, R, C> extends Data<D, R, C> {
    public BlockedData(List<ZFrame<D, R, C>> zFrames) {
        super(zFrames);
    }
    @SafeVarargs
    public BlockedData(ZFrame<D, R, C>... zFrames) {
        super(zFrames);
    }
}
