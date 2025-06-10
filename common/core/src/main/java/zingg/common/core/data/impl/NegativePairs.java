package zingg.common.core.data.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.data.AData;

import java.util.List;

public class NegativePairs<D, R, C> extends AData<D, R, C> {
    public NegativePairs(List<ZFrame<D, R, C>> zFrames) {
        super(zFrames);
    }
    @SafeVarargs
    public NegativePairs(ZFrame<D, R, C>... zFrames) {
        super(zFrames);
    }
}
