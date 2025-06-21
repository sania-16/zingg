package zingg.common.core.data;

import zingg.common.client.ZFrame;

public interface DataManager<D, R, C> {
    Data<D, R, C> createData(ZFrame<D, R, C>... zFrame);

}
