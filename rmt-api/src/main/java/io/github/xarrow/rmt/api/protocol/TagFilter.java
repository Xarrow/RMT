package io.github.xarrow.rmt.api.protocol;

public interface TagFilter {
    String[] getFilterRules();
    boolean satisfy();
}
