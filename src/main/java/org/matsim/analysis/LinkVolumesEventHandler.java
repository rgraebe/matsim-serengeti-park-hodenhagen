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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LinkVolumesEventHandler implements LinkLeaveEventHandler {

    Id<Link> watchedLink = Id.createLinkId("908198570004f"); // link just before entrance
    public final Map<String,Double> vehicleLeaveLinkEvent = new HashMap<>();

    @Override
    public void handleEvent(LinkLeaveEvent linkLeaveEvent) {

        double leaveTime = linkLeaveEvent.getTime() / 3600.;
        final Id<Vehicle> vehicleId = linkLeaveEvent.getVehicleId();
        watchedLink = linkLeaveEvent.getLinkId();

        if (linkLeaveEvent.getLinkId().equals(watchedLink)) {
            vehicleLeaveLinkEvent.put( vehicleId.toString(), leaveTime );
//            vehicleLeaveLinkEvent.merge(String.valueOf(leaveTime), 1, Integer::sum ); // to create bins, but use Map<String,Integer>
        }

    }
}
