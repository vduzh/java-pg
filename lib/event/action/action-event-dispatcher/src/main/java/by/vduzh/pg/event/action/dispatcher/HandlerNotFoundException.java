package by.vduzh.pg.event.action.dispatcher;

import java.io.Serial;

public class HandlerNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5133306726586764742L;

    public HandlerNotFoundException(String s) {
        super(s);
    }
}
