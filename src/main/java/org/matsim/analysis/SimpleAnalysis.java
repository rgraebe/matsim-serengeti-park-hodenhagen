package org.matsim.analysis;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;

public class SimpleAnalysis {

    public static void main(String[] args) {

        var handler = new SimplePersonEventHandler(); // you can have multiple handlers
        var manager = EventsUtils.createEventsManager();
        manager.addHandler(handler); // tell the manager to use our handler

        EventsUtils.readEvents(manager, "scenarios/serengeti-park-v1.0/output/output-serengeti-park-v1.0-run1/serengeti-park-v1.0-run1.output_events.xml.gz");

    }
}
