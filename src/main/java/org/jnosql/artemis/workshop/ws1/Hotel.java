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

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Entity;
import org.jnosql.artemis.Id;

@Entity
public class Hotel {

    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private Long stars;

    Hotel() {
    }
    private Hotel(String name, Long stars) {
        this.name = name;
        this.stars = stars;

    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getStars() {
        return stars;
    }

    public static Hotel of(String name, Long stars) {
        return new Hotel(name, stars);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stars=" + stars +
                '}';
    }
}
