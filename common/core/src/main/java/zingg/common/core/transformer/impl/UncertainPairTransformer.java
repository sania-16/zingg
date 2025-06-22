package zingg.common.core.transformer.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.client.util.ColName;
import zingg.common.client.util.ColValues;
import zingg.common.core.transformer.AZFrameTransformer;

public class UncertainPairTransformer<D, R, C> extends AZFrameTransformer<D, R, C> {
    public static final Log LOG = LogFactory.getLog(UncertainPairTransformer.class);

    @Override
    public ZFrame<D, R, C> transformZFrame(ZFrame<D, R, C> data) {
        ZFrame<D,R,C> pos = data.filter(data.equalTo(ColName.PREDICTION_COL, ColValues.IS_MATCH_PREDICTION));
        pos = pos.sortAscending(ColName.SCORE_COL);
        if (LOG.isDebugEnabled()) {
            LOG.debug("num pos " + pos.count());
        }
        pos = pos.limit(10);
        ZFrame<D,R,C> neg = data.filter(data.equalTo(ColName.PREDICTION_COL, ColValues.IS_NOT_A_MATCH_PREDICTION));
        neg = neg.sortDescending(ColName.SCORE_COL).cache();
        if (LOG.isDebugEnabled()) {
            LOG.debug("num neg " + neg.count());
        }
        neg = neg.limit(10);
        return pos.union(neg);
    }
}
