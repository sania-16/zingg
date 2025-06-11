package zingg.common.core.queue.impl;

import zingg.common.core.queue.AZFrameQueue;

import java.util.ArrayList;

public class IntermediateZFrameQueue<D, R, C> extends AZFrameQueue<D, R, C> {

    private static IntermediateZFrameQueue intermediateZFrameQueue = null;

    private IntermediateZFrameQueue() {
        super(new ArrayList<>());
    }

    public static IntermediateZFrameQueue getInstance() {
        if (intermediateZFrameQueue == null) {
            intermediateZFrameQueue = new IntermediateZFrameQueue();
        }
        return intermediateZFrameQueue;
    }
}
