package zingg.common.core.transformer.map;

import zingg.common.client.IArguments;
import zingg.common.client.ZinggClientException;
import zingg.common.core.context.Context;

import java.util.HashMap;
import java.util.Map;

public abstract class TransformerMap<S, D, R, C, T, V> {
    protected final Map<String, V> transformers;
    protected final Context<S, D, R, C, T> context;
    protected final IArguments arguments;

    public TransformerMap(Context<S, D, R, C, T> context, IArguments arguments) {
        this.transformers = new HashMap<>();
        this.context = context;
        this.arguments = arguments;
        init();

    }
    public abstract void init() throws ZinggClientException;

    public V getTransformer(String name) {
        return transformers.get(name);
    }
}
