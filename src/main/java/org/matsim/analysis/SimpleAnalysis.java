package org.matsim.analysis;

import org.apache.log4j.Logger;
import org.matsim.core.events.EventsUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SimpleAnalysis {

    private static final Logger log = Logger.getLogger(LinkVolumesEventHandler.class);

    public static void main(String[] args) {

        var handler = new SimplePersonEventHandler(); // you can have multiple handlers
        var manager = EventsUtils.createEventsManager();
        manager.addHandler(handler); // tell the manager to use our handler

        EventsUtils.readEvents(manager, "scenarios/serengeti-park-v1.0/output/output-serengeti-park-v1.0-run1/serengeti-park-v1.0-run1.output_events.xml.gz");

        // code at: https://github.com/Janekdererste/matsim-serengeti-park-hodenhagen/blob/3f8b2dbdaf92c08746a25c6dbad8686954d0e493/src/main/java/org/matsim/analyze/LinkEventHandler.java
        var linkHandler = new LinkVolumesEventHandler();
        manager.addHandler(linkHandler);
        EventsUtils.readEvents(manager, "scenarios/serengeti-park-v1.0/output/output-serengeti-park-v1.0-run1/serengeti-park-v1.0-run1.output_events.xml.gz");

        var linkHashmap = linkHandler.vehicleLeaveLinkEvent;
        writeLinkVolumes(linkHashmap);
    }

    static void writeLinkVolumes(Map<String, Double> linkMap) {
        String path = "scenarios/serengeti-park-v1.0/output/linkVolumes.csv";
        File file = new File(path);
        BufferedWriter bw1;
        try {
            bw1 = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bw1.write("VehicleId;Time [h]");
            bw1.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry<String, Double> entry : linkMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            try {
                bw1.write(key + ";" + value );
                bw1.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            bw1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Done");
        log.info("Link volumes written to 'scenarios/serengeti-park-v1.0/output/linkVolumes.csv'");
    }
}
