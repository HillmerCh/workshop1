/*
 * Copyright (c) 2017 Otávio Santana and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *
 * Hillmer Chona
 */
package org.jnosql.artemis.workshop.ws1;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.jnosql.artemis.graph.EdgeEntity;
import org.jnosql.artemis.graph.GraphTemplate;


public class HotelApp {


    private static final String EDGE_BOOKED = "BOOKED";
    private static final String EDGE_RATING = "RATING";
    private static final String EDGE_TYPE = "TYPE";

    private static final String TYPE_VACATION = "Vacation";
    private static final String TYPE_BUSINESS = "Business";

    private static final String RATING_BAD = "Bad";
    private static final String RATING_GOOD = "Good";
    private static final String RATING_EXCELLENT = "Excellent";


    private static Guest getGuest(String fullName, GraphTemplate graphTemplate) {
        return graphTemplate.getTraversalVertex().hasLabel("Guest")
                .has("fullName", fullName)
                .<Guest>next()
                .orElseThrow(() -> new IllegalStateException("Entity does not find"));
    }

    private static Hotel getHotel(String name, GraphTemplate graphTemplate) {
        return graphTemplate.getTraversalVertex().hasLabel("Hotel")
                .has("name", name)
                .<Hotel>next()
                .orElseThrow(() -> new IllegalStateException("Entity does not find"));
    }

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            GraphTemplate graphTemplate = container.select(GraphTemplate.class).get();


            System.out.println("0. ELEMENTS BEFORE RUNS VERTICES IN GRAPH DATABASE");
            graphTemplate.getTraversalVertex().stream().forEach(System.out::println);


         // Graph thinkerpopGraph = container.select(Graph.class).get();
          //1. Creating vertex
            Guest hillmer = Guest.of("Hillmer");
            hillmer = graphTemplate.insert(hillmer);


            Guest amy = Guest.of("Amy");
            amy = graphTemplate.insert(amy);

            Hotel parkCentral = Hotel.of("Park Central", 3L);
            parkCentral = graphTemplate.insert(parkCentral);

            Hotel hiltonSF = Hotel.of("Hilton San Francisco", 4L);
            hiltonSF = graphTemplate.insert(hiltonSF);

            //thinkerpopGraph.tx().commit();

            System.out.println(hillmer);

            hillmer = getGuest("Hillmer", graphTemplate);
            System.out.println(hillmer);


            amy = getGuest("Amy", graphTemplate);
            parkCentral = getHotel("Park Central", graphTemplate);
            hiltonSF = getHotel("Hilton San Francisco", graphTemplate);

            //Printing vertex from our graph
            System.out.println("1. VERTICES IN GRAPH DATABASE");
            graphTemplate.getTraversalVertex().stream().forEach(System.out::println);


            //2. Adding edges (Relationships) between vertices

            graphTemplate.edge(hillmer, EDGE_BOOKED, parkCentral);
            graphTemplate.edge(hillmer, EDGE_BOOKED, hiltonSF);
            //graphTemplate.edge(hillmer, EDGE_BOOKED, hiltonSF);
            graphTemplate.edge(amy, EDGE_BOOKED, hiltonSF);

            //Printing vertex from our graph
            System.out.println("2. EDGES IN GRAPH DATABASE");

            graphTemplate.getTraversalEdge().stream().forEach(System.out::println);

            //3. Adding additional information between vertices: The method edge either find or create an Edge


            graphTemplate.edge(amy, EDGE_BOOKED, hiltonSF).add(EDGE_TYPE, TYPE_VACATION);
            graphTemplate.edge(amy, EDGE_BOOKED, hiltonSF).add(EDGE_RATING, RATING_GOOD);

            graphTemplate.edge(hillmer, EDGE_BOOKED, parkCentral).add(EDGE_TYPE, TYPE_VACATION);
            graphTemplate.edge(hillmer, EDGE_BOOKED, parkCentral).add(EDGE_RATING, RATING_GOOD);

            //hillmer booked hilton two times, first one for vacation, and second one for business
            //in this use case only the last booking is saved, I supposed the last one replaces the first one

            graphTemplate.edge(hillmer, EDGE_BOOKED, hiltonSF).add(EDGE_TYPE, TYPE_VACATION);
            graphTemplate.edge(hillmer, EDGE_BOOKED, hiltonSF).add(EDGE_RATING, RATING_GOOD);

            graphTemplate.edge(hillmer, EDGE_BOOKED, hiltonSF).add(EDGE_TYPE, TYPE_BUSINESS);
            graphTemplate.edge(hillmer, EDGE_BOOKED, hiltonSF).add(EDGE_RATING, RATING_EXCELLENT);



            EdgeEntity<Guest, Hotel> amyBookedHilton = graphTemplate.edge(amy, EDGE_BOOKED, hiltonSF);

            //System.out.println(amyBookedHilton.getId());
            //thinkerpopGraph.tx().commit();

            System.out.println("");
            System.out.println("3. ALL VERTICES WITH ADDITIONAL INFORMATION IN THEIR EDGES");
            System.out.println("");

            graphTemplate.getTraversalEdge().stream().forEach(p -> {

                        System.out.println(p);
                        p.getProperties().forEach(System.out::println);
                        System.out.println("");
                    }
            );

/*
            System.out.println("4. ALL VERTICES THAT BOOKED HOTEL ");

            graphTemplate.getTraversalVertex().inE(EDGE_BOOKED)

                    .stream().forEach(p-> {
                System.out.println(p) ;
                ;p.getProperties().forEach(System.out::println);});

*/
            /*
            System.out.println("VERTEX THAT BOOKED HOTEL WITH RATING GOOD");

            graphTemplate.getTraversalVertex().inE(EDGE_BOOKED).has(EDGE_RATING, RATING_GOOD)

                    .stream().forEach(p-> {
                        System.out.println(p) ;
            ;p.getProperties().forEach(System.out::println);});*/

            /*
            System.out.println("VERTEX THAT BOOKED HOTEL WITH RATING EXCELLENT");

            graphTemplate.getTraversalVertex().inE(EDGE_BOOKED).has(EDGE_RATING, RATING_EXCELLENT)

                    .stream().forEach(p-> {
                System.out.println(p) ;
                ;p.getProperties().forEach(System.out::println);});
                */
        }


    }
}
