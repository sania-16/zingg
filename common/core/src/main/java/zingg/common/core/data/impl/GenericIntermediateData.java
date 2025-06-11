package zingg.common.core.data.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.data.Data;

import java.util.List;

public class GenericIntermediateData<D, R, C> extends Data<D, R, C> {
    public GenericIntermediateData(List<ZFrame<D, R, C>> zFrames) {
        super(zFrames);
    }

    @SafeVarargs
    public GenericIntermediateData(ZFrame<D, R, C>... zFrames) {
        super(zFrames);
    }
}
