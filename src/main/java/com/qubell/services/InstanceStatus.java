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

import java.util.Map;

/**
 * Instance status information
 * @author Alex Krupnov
 */
public class InstanceStatus {
    private Instance instance;
    private String version;
    private InstanceStatusCode status;
    private Workflow currentWorkflow;
    private String errorMessage;
    private Map<String, Object> returnValues;
    private Application application;

    /**
     * @return instance of app, owning the status data
     */
    public Instance getInstance() {
        return instance;
    }

    /**
     * Sets owning instance
     * @param instance instance reference
     */
    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    /**
     * Version of app, running on instance
     * @return version value
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets version of instance
     * @param version value of version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Current instance status, enumeration
     * @return value of current status
     */
    public InstanceStatusCode getStatus() {
        return status;
    }

    /**
     * Sets status
     * @param status status value
     */
    public void setStatus(InstanceStatusCode status) {
        this.status = status;
    }

    /**
     * Information about currently running workflow
     * @return workflow or null
     */
    public Workflow getCurrentWorkflow() {
        return currentWorkflow;
    }

    /**
     * Sets current workflow
     * @param currentWorkflow workflow value
     */
    public void setCurrentWorkflow(Workflow currentWorkflow) {
        this.currentWorkflow = currentWorkflow;
    }

    /**
     * Error message, populated when error happened
     * @return message or null
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets error message
     * @param errorMessage error message value
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Instance return values map
     * @return nullable list of instance return values
     */
    public Map<String, Object> getReturnValues() {
        return returnValues;
    }

    /**
     * Sets instance return values
     * @param returnValues return values map
     */
    public void setReturnValues(Map<String, Object> returnValues) {
        this.returnValues = returnValues;
    }

    /**
     * Application, running by instance
     * @return app information
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets instance application
     * @param application application value
     */
    public void setApplication(Application application) {
        this.application = application;
    }
}
