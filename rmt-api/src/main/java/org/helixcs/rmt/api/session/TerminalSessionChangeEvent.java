package org.helixcs.rmt.api.session;

import java.util.EventObject;

//todo
public class TerminalSessionChangeEvent extends EventObject {


    private SessionChangeType sessionChangeType;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public TerminalSessionChangeEvent(Object source) {
        super(source);
    }
    //....


}
