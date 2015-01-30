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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Response of instance launch WS call
 * @author Alex Krupnov
 */
@XmlRootElement
@XmlType(name = "", propOrder = {"id"})
public class LaunchInstanceResponse {

    private String id;

    private List<String> warnings;

    private List<String> errors;

    /**
     * Gets instance id
     * @return instance id value
     */
    public String getId() {
        return id;
    }

    /**
     * Sets instance id
     * @param id instance id value
     */
    public void setId(String id) {
        this.id = id;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
