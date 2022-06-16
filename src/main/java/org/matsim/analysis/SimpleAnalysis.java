package org.matsim.analysis;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class SimpleAnalysis {

    public static void main(String[] args) {

        var handler = new SimplePersonEventHandler(); // you can have multiple handlers
        var manager = EventsUtils.createEventsManager();
        manager.addHandler(handler); // tell the manager to use our handler

        EventsUtils.readEvents(manager, "scenarios/serengeti-park-v1.0/output/output-serengeti-park-v1.0-run1/serengeti-park-v1.0-run1.output_events.xml.gz");

        // todo: finish link event handler
        // code at: https://github.com/Janekdererste/matsim-serengeti-park-hodenhagen/blob/3f8b2dbdaf92c08746a25c6dbad8686954d0e493/src/main/java/org/matsim/analyze/LinkEventHandler.java
        var linkHandler = new LinkVolumesEventHandler();
        manager.addHandler(linkHandler);
        EventsUtils.readEvents(manager, "scenarios/serengeti-park-v1.0/output/output-serengeti-park-v1.0-run1/serengeti-park-v1.0-run1.output_events.xml.gz");

    }
}
