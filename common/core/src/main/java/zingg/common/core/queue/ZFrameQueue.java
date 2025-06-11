package zingg.common.core.queue;

import zingg.common.client.ZFrame;

public interface ZFrameQueue<D, R, C> {
    void addToQueue(ZFrame<D, R, C> data);
    void removeFromQueue(ZFrame<D, R, C> data);
    ZFrame<D, R, C> getFromQueue(String name);
}
