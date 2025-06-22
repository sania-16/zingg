package zingg.common.core.transformer.impl;

import zingg.common.client.IArguments;
import zingg.common.client.ZFrame;
import zingg.common.client.ZinggClientException;
import zingg.common.core.context.Context;
import zingg.common.core.data.Data;
import zingg.common.core.model.Model;
import zingg.common.core.transformer.IDataZFrameTransformer;
import zingg.common.core.transformer.ZFrameTypes;

public abstract class PredictionTransformer<S, D, R, C, T> implements IDataZFrameTransformer<D, R, C> {

    private final Context<S, D, R, C, T> context;
    private final IArguments arguments;
    private Model<S, T, D, R, C> model;

    public PredictionTransformer(Context<S, D, R, C, T> context, IArguments arguments) {
        this.context = context;
        this.arguments = arguments;
    }

    @Override
    public ZFrame<D, R, C> transform(Data<D, R, C> data) throws ZinggClientException, Exception {
        if (model == null) {
            ZFrame<D, R, C> positivePairs = data.getZFrameByName(ZFrameTypes.POSITIVE_PAIRS.name());
            ZFrame<D, R, C> negativePairs = data.getZFrameByName(ZFrameTypes.NEGATIVE_PAIRS.name());
            model = context.getModelUtil().createModel(positivePairs, negativePairs, true, arguments);
        }
        ZFrame<D, R, C> blocks = data.getZFrameByName(ZFrameTypes.BLOCKS.name());
        return model.predict(blocks);
    }

    public void setModel(Model<S, T, D, R, C> model) {
        this.model = model;
    }
}
