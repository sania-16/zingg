package zingg.common.core.processor.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import zingg.common.client.ZFrame;
import zingg.common.core.processor.AZFrameProcessor;

public class PositivePairGenerator<S, D, R, C, T> extends AZFrameProcessor<D, R, C> {
    private static final Log LOG = LogFactory.getLog(PositivePairGenerator.class);

    public PositivePairGenerator() {
    }

    @Override
    public ZFrame<D, R, C> processZFrame(ZFrame<D, R, C> data) {
        ZFrame<D, R, C> positivePairs = null;
        //perform logic to calculate positivePairs
        return positivePairs;
    }
}
