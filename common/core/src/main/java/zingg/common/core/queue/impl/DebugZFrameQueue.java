package zingg.common.core.queue.impl;

import zingg.common.client.ZFrame;
import zingg.common.core.data.Data;
import zingg.common.core.queue.AZFrameQueue;

import java.util.ArrayList;

public class DebugZFrameQueue<D, R, C> extends AZFrameQueue<D, R, C> {

    private static DebugZFrameQueue debugZFrameQueue = null;

    private DebugZFrameQueue() {
        super(new ArrayList<>());
    }

    public static DebugZFrameQueue getInstance() {
        if (debugZFrameQueue == null) {
            debugZFrameQueue = new DebugZFrameQueue();
        }
        return debugZFrameQueue;
    }

    public void notifyWorker() {
        for (ZFrame<D, R, C> data : dataList) {
            //perform debugging by debugger
            dataList.remove(data);
        }
    }
}
