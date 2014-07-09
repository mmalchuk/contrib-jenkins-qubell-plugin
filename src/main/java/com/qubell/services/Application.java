/*
 * Copyright 2013 Qubell, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qubell.services;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Qubell application definition
 *
 * @author Alex Krupnov
 */
public class Application implements TypeAheadDatum {
    private String id;
    private String name;
    private Organization organization;

    /**
     * {@inheritDoc}
     */
    public String getValue() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getTokens() {
        List<String> tokens = new ArrayList<String>();
        tokens.add(id);
        if (name != null) {
            Collections.addAll(tokens, name.split(" "));
        }
        return tokens;
    }

    /**
     * Inits app with id, leaving name blank
     *
     * @param id
     */
    public Application(String id) {
        this.id = id;
    }

    /**
     * Inits application with id and name
     *
     * @param id           if of app
     * @param name         app name
     * @param organization organization for app
     */
    public Application(String id, String name, Organization organization) {
        this.id = id;
        this.name = name;
        this.organization = organization;
    }

    /**
     * @return application id
     */
    public String getId() {
        return id;
    }

    /**
     * @return application name
     */
    public String getName() {
        return name;
    }

    /**
     * Organization where app belongs to
     *
     * @return organization or null
     */
    public Organization getOrganization() {
        return organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
