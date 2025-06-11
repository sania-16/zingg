package zingg.common.core.data;

import zingg.common.client.ZFrame;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Data<D, R, C> {
    private String name;
    private final long timestamp;

    private final List<ZFrame<D, R, C>> zFrames;

    public Data(List<ZFrame<D, R, C>> zFrames) {
        this.timestamp = Instant.now().getEpochSecond();
        this.zFrames = zFrames;
    }

    @SafeVarargs
    public Data(ZFrame<D, R, C>... data) {
        zFrames = new ArrayList<>();
        zFrames.addAll(Arrays.asList(data));
        this.timestamp = Instant.now().getEpochSecond();
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

    public void cache() {
        zFrames.replaceAll(ZFrame::cache);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getTimestamp() {
        return timestamp;
    }
}
