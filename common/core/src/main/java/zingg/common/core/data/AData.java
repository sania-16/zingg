package zingg.common.core.data;

import zingg.common.client.ZFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AData<D, R, C> {

    private String name;
    private boolean isDebuggable;
    private boolean isExplainable;

    private final List<ZFrame<D, R, C>> zFrames;

    public AData(List<ZFrame<D, R, C>> zFrames) {
        this.zFrames = zFrames;
    }

    @SafeVarargs
    public AData(ZFrame<D, R, C>... data) {
        zFrames = new ArrayList<>();
        zFrames.addAll(Arrays.asList(data));
    }

    public long count() {
        long count = 0;
        for (ZFrame<D, R, C> zFrame : zFrames) {
            count += zFrame.count();
        }
        return count;
    }

    public List<ZFrame<D, R, C>> getzFrames() {
        return this.zFrames;
    }

    public AData<D, R, C> cache() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDebuggable() {
        return isDebuggable;
    }

    public boolean isExplainable() {
        return isExplainable;
    }

    public void setDebuggable(boolean debuggable) {
        isDebuggable = debuggable;
    }

    public void setExplainable(boolean explainable) {
        isExplainable = explainable;
    }
    //    public AData<D, R, C> cache() {
//        List<ZFrame<D, R, C>> cachedZFrames = new ArrayList<>();
//        for (ZFrame<D, R, C> zFrame : zFrames) {
//            cachedZFrames.add(zFrame.cache());
//        }
//        return new
//    }
}
