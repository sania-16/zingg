package zingg.common.core.queue;

import zingg.common.core.ZinggException;
import zingg.common.core.data.Data;

import java.util.List;

public abstract class ADataQueue<D, R, C> implements DataQueue<D, R, C> {
    protected final List<Data<D, R, C>> dataList;

    public ADataQueue(List<Data<D, R, C>> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void addToQueue(Data<D, R, C> data) {
        dataList.add(data);
    }

    @Override
    public void removeFromQueue(Data<D, R, C> data) {
        dataList.remove(data);

    }

    @Override
    public Data<D, R, C> getFromQueue(String name) {
        for (Data<D, R, C> data : dataList) {
            if (name.equalsIgnoreCase(data.getName())) {
                return data;
            }
        }
        throw new ZinggException("No such data objects exists in queue " + name);
    }

}
