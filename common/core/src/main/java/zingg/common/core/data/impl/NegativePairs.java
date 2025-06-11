package zingg.common.core.data.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.data.Data;

import java.util.List;

public class NegativePairs<D, R, C> extends Data<D, R, C> {
    public NegativePairs(List<ZFrame<D, R, C>> zFrames) {
        super(zFrames);
    }
    @SafeVarargs
    public NegativePairs(ZFrame<D, R, C>... zFrames) {
        super(zFrames);
    }
}
