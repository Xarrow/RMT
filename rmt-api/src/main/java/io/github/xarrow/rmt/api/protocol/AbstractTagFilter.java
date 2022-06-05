package io.github.xarrow.rmt.api.protocol;

public abstract class AbstractTagFilter implements TagFilter {
    public String[] getFilterRules() {
        return new String[0];
    }

    public boolean satisfy() {
        return true;
    }
}
