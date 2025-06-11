package zingg.common.core.data.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.data.Data;

import java.util.List;

public class PositivePairs<D, R, C> extends Data<D, R, C> {
    public PositivePairs(List<ZFrame<D, R, C>> zFrames) {
        super(zFrames);
    }
    @SafeVarargs
    public PositivePairs(ZFrame<D, R, C>... zFrames) {
        super(zFrames);
    }
}
