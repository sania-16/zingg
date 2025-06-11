package zingg.common.core.queue;

import zingg.common.core.data.Data;

public interface DataQueue<D, R, C> {
    void addToQueue(Data<D, R, C> data);
    void removeFromQueue(Data<D, R, C> data);
    Data<D, R, C> getFromQueue(String name);
}
