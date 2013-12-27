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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Response object for instnace status
 * @author Alex Krupnov
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstanceStatusResponse {
    private String id;
    private InstanceStatus status;
    private String name;
    private String version;
    private Map<String, Object> parameters;
    private String ownerId;
    private Workflow workflow;
    private Map<String, Object> returnValues;
    private String errorMessage;
    private String applicationId;
    private String environmentId;

    private String percentComplete;

    /**
     * @return percent complete as string
     */
    public String getPercentComplete() {
        return percentComplete;
    }

    /**
     * Sets percent complete
     * @param percentComplete percent compete value
     */
    public void setPercentComplete(String percentComplete) {
        this.percentComplete = percentComplete;
    }

    /**
     * Instance id
     * @return instance id
     */
    public String getId() {
        return id;
    }

    /**
     * Sests intance id
     * @param id instance id value
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Instance status
     * @return value of status enum
     */
    public InstanceStatus getStatus() {
        return status;
    }

    /**
     * Sets status
     * @param status status value
     */
    public void InstanceStatus(InstanceStatus status) {
        this.status = status;
    }

    /**
     * Instance friendly name
     * @return name or null
     */
    public String getName() {
        return name;
    }


    /**
     * Sets instance friendly name
     * @param name name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Instance app version
     * @return app version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets app version
     * @param version version value
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Instance input parameters
     * @return map of input params
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Sets input parameters of instance status
     * @param parameters params map
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * Instance owner
     * @return owner id
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Sets instance owner
     * @param ownerId owner id value
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Currently executing workflow
     * @return value or workflow or null
     */
    public Workflow getWorkflow() {
        return workflow;
    }

    /**
     * Sets current workflow
     * @param workflow value of workflow
     */
    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    /**
     * Instance return values
     * @return return values map
     */
    public Map<String, Object> getReturnValues() {
        return returnValues;
    }

    /**
     * Sets return values
     * @param returnValues return values map
     */
    public void setReturnValues(Map<String, Object> returnValues) {
        this.returnValues = returnValues;
    }

    /**
     * Instance error message
     * @return message or null when no error happened
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets error message
     * @param errorMessage message value
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Instance application id
     * @return id of app
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Sets instance application id
     * @param applicationId app id value
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Id of environment where instance is running
     * @return value of environment id
     */
    public String getEnvironmentId() {
        return environmentId;
    }

    /**
     * Sets environment i
     * @param environmentId id value
     */
    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }
}
