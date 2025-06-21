package zingg.common.core.data;

import zingg.common.client.ZFrame;

public class DataManagerImpl<D, R, C> {

    @SafeVarargs
    public static <D, R, C> Data<D, R, C> createData(ZFrame<D, R, C>... zFrame) {
        return new Data<>(zFrame);
    }
}
