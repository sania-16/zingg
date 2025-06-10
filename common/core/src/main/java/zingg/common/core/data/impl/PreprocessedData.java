package zingg.common.core.data.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.data.AData;

import java.util.List;

public class PreprocessedData<D, R, C> extends AData<D, R, C> {
    public PreprocessedData(List<ZFrame<D, R, C>> zFrames) {
        super(zFrames);
    }
    @SafeVarargs
    public PreprocessedData(ZFrame<D, R, C>... zFrames) {
        super(zFrames);
    }
}
