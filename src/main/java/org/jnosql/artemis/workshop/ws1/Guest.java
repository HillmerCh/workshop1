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
public class Guest {

    @Id
    private Long id;
    @Column
    private String fullName;

    Guest() {
    }

    private Guest(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public static Guest of(String fullName) {
        return new Guest(fullName);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
