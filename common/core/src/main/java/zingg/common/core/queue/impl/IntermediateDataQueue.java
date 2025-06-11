package zingg.common.core.queue.impl;

import zingg.common.core.queue.ADataQueue;

import java.util.ArrayList;

public class IntermediateDataQueue<D, R, C> extends ADataQueue<D, R, C> {

    private static IntermediateDataQueue intermediateDataQueue = null;

    private IntermediateDataQueue() {
        super(new ArrayList<>());
    }

    public static IntermediateDataQueue getInstance() {
        if (intermediateDataQueue == null) {
            intermediateDataQueue = new IntermediateDataQueue();
        }
        return intermediateDataQueue;
    }
}
