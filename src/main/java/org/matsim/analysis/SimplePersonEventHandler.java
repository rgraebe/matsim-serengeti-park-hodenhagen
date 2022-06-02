package org.matsim.analysis;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonArrivalEvent;
import org.matsim.api.core.v01.events.PersonDepartureEvent;
import org.matsim.api.core.v01.events.handler.PersonArrivalEventHandler;
import org.matsim.api.core.v01.events.handler.PersonDepartureEventHandler;
import org.matsim.api.core.v01.population.Person;

import java.util.HashMap;
import java.util.Map;

public class SimplePersonEventHandler implements PersonDepartureEventHandler, PersonArrivalEventHandler {

    private final Map<Id<Person>, Double> departureTimeByPerson = new HashMap<>();
    @Override
    public void handleEvent(PersonDepartureEvent personDepartureEvent) {

//        System.out.println("Departure: " + personDepartureEvent.getTime() + ", " + personDepartureEvent.getPersonId());

        System.out.println("AgentDepartureEvent -- time: " + personDepartureEvent.getTime() +
                                            " -- linkId: " + personDepartureEvent.getLinkId() +
                                            " -- personId: " + personDepartureEvent.getPersonId());

        // save PersonId and time of departure to use in calculation of travel time
        departureTimeByPerson.put(personDepartureEvent.getPersonId(), personDepartureEvent.getTime());

    }

    @Override
    public void handleEvent(PersonArrivalEvent personArrivalEvent) {

//        System.out.println("Arrival: " + personArrivalEvent.getTime() + ", " + personArrivalEvent.getPersonId());

        System.out.println("PersonArrivalEvent -- time: " + personArrivalEvent.getTime() +
                                            " -- linkId: " + personArrivalEvent.getLinkId() +
                                           " -- personId: " + personArrivalEvent.getPersonId());

        double departureTime = departureTimeByPerson.get(personArrivalEvent.getPersonId());
        double travelTime = personArrivalEvent.getTime() - departureTime;
        System.out.println("Travel time of person " + personArrivalEvent.getPersonId() + " is " + travelTime + " s.");

    }

}
