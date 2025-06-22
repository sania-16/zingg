package zingg.common.core.transformer.impl;

import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.client.cols.ISelectedCols;
import zingg.common.core.filter.IFilter;
import zingg.common.core.transformer.IZFrameTransformer;

public class PredictionFilterTransformer implements IZFrameTransformer {

    private final IFilter filter;
    private final ISelectedCols selectedCols;

    public PredictionFilterTransformer(IFilter filter, ISelectedCols selectedCols) {
        this.filter = filter;
        this.selectedCols = selectedCols;
    }

    @Override
    public ZFrame transform(ZFrame data) throws Exception, ZinggClientException {
        ZFrame filtered = filter.filter(data);
        return filtered.select(selectedCols.getCols());
    }
}
