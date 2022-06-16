package org.matsim.analysis;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.vehicles.Vehicle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class LinkVolumesEventHandler implements LinkLeaveEventHandler {

    private static final Logger log = Logger.getLogger(LinkVolumesEventHandler.class);
    Id<Link> watchedLink = Id.createLinkId("908198570004f"); // link just before entrance
    final Map<String,Double> vehicleLeaveLinkEvent = new HashMap<>();

    @Override
    public void handleEvent(LinkLeaveEvent linkLeaveEvent) {

        final double leaveTime = linkLeaveEvent.getTime();
        final Id<Vehicle> vehicleId = linkLeaveEvent.getVehicleId();
        watchedLink = linkLeaveEvent.getLinkId();

        if (linkLeaveEvent.getLinkId() == watchedLink) {
            vehicleLeaveLinkEvent.put( vehicleId.toString(), leaveTime );
        }

        {
            String path = "scenarios/serengeti-park-v1.0/output/linkVolumes.csv";
            File file = new File(path);
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(file)); // todo

            bw1.write("VehicleId;Time [s]");
            bw1.newLine();

            for (Map.Entry<String, Double> entry : vehicleLeaveLinkEvent.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                bw1.write(key + ";" + value );

            }
            bw1.newLine();

            bw1.close();
            log.info("Done");
            log.info("Link volumes written to " + path);
        }

    }
}
