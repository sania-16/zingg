package zingg.common.core.queue;

import zingg.common.client.ZFrame;
import zingg.common.core.ZinggException;

import java.util.List;

public class AZFrameQueue<D, R, C> implements ZFrameQueue<D, R, C> {
    protected final List<ZFrame<D, R, C>> dataList;

    public AZFrameQueue(List<ZFrame<D, R, C>> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void addToQueue(ZFrame<D, R, C> data) {
        dataList.add(data);
    }

    @Override
    public void removeFromQueue(ZFrame<D, R, C> data) {
        dataList.remove(data);

    }

    @Override
    public ZFrame<D, R, C> getFromQueue(String name) {
        for (ZFrame<D, R, C> data : dataList) {
            /*
            if (name.equalsIgnoreCase(data.getName())) {
                return data;
            }*/
            return data;
        }
        throw new ZinggException("No such ZFrame exists in queue " + name);
    }
}
