package zingg.common.core.transformer.map;

import zingg.common.client.IArguments;
import zingg.common.core.context.Context;

import java.util.HashMap;
import java.util.Map;

public abstract class TransformerMap<S, D, R, C, T, V> {
    private final Map<String, V> transformers;
    private final Context<S, D, R, C, T> context;
    private final IArguments arguments;

    public TransformerMap(Context<S, D, R, C, T> context, IArguments arguments) {
        this.transformers = new HashMap<>();
        this.context = context;
        this.arguments = arguments;
        init();

    }
    public abstract void init();

    V getTransformer(String name) {
        return transformers.get(name);
    }
}
