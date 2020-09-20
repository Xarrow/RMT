package org.helixcs.rmt.api.protocol;

public interface TagFilter {
    String[] getFilterRules();
    boolean satisfy();
}
