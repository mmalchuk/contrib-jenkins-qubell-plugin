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

package com.qubell.services.ws;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * WS wrapper for Qubell application
 * @author Alex Krupnov
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {
    private String id;
    private String name;

    /**
     * Application id
     * @return value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets app id
     * @param id value to be set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Application name
     * @return name of app or null
     */
    public String getName() {
        return name;
    }

    /**
     * Sets new name for app
     * @param name value to be set
     */
    public void setName(String name) {
        this.name = name;
    }
}
