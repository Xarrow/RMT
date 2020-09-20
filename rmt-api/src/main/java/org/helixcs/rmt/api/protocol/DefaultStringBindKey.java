package org.helixcs.rmt.api.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class DefaultStringBindKey extends AbstractTagFilter {
    private String key;

}
