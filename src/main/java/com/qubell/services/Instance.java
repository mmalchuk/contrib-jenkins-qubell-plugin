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

/**
 * Represents Qubell instnace
 * @author Alex Krupnov
 */
public class Instance {
    private String id;
    private String name;

    /**
     * Inits instance with id, leaving name blank
     * @param id
     */
    public Instance(String id) {
        this.id = id;
    }

    /**
     * Inits instance with id and name
     * @param id
     * @param name
     */
    public Instance(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return instance id
     */
    public String getId() {
        return id;
    }

    /**
     * @return instance friendly name
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instance instance = (Instance) o;

        return id.equals(instance.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
