package zingg.common.core.queue.impl;

import zingg.common.core.data.Data;
import zingg.common.core.queue.ADataQueue;

import java.util.ArrayList;

public class DebugDataQueue<D, R, C> extends ADataQueue<D, R, C> {

    private static DebugDataQueue debugDataQueue = null;

    //attached worker is debugger

    private DebugDataQueue() {
        super(new ArrayList<>());
    }

    public static DebugDataQueue getInstance() {
        if (debugDataQueue == null) {
            debugDataQueue = new DebugDataQueue();
        }
        return debugDataQueue;
    }

    public void notifyWorker() {
        for (Data<D, R, C> data : dataList) {
            //perform debugging by debugger
            dataList.remove(data);
        }
    }
}
