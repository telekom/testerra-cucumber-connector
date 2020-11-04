package eu.tsystems.mms.tic.testerra.plugins.cucumber.handlers;

import eu.tsystems.mms.tic.testframework.events.MethodEndEvent;

public interface TagHandler {
    void handle(MethodEndEvent methodEndEvent);
}
