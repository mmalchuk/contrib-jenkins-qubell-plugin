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

import com.qubell.services.exceptions.*;

import java.util.List;
import java.util.Map;

/**
 * General facade for Qubell API operations
 * @author Alex Krupnov
 */
public interface QubellFacade {
    /**
     * Launches a Qubell app instnace
     * @param instanceSpecification instnance specification for launch
     * @param launchSettings additional launch settings
     * @return valid instance information, see {@link Instance}
     * @throws InvalidCredentialsException when credentials are invalid
     */
    Instance launchInstance(InstanceSpecification instanceSpecification, LaunchSettings launchSettings) throws InvalidCredentialsException;

    /**
     * Updates application manifest
     * @param application application to update
     * @param manifest yaml manifest
     * @return new version of application
     * @throws InvalidCredentialsException when credentials are invalid
     * @throws InvalidInputException when manifest is invalid
     */
    Integer updateManifest(Application application, Manifest manifest) throws InvalidCredentialsException, InvalidInputException;

    /**
     * Gets status of instance, see {@link InstanceStatus}
     * @param instance instance to query status
     * @return valid instance status object
     * @throws InvalidCredentialsException when credentials are invalid
     */
    InstanceStatus getStatus(Instance instance) throws InvalidCredentialsException;

    /**
     * Runs a command on instance with extra parameter
     * @param instance valid instance
     * @param commandName name of command/workflow
     * @param parameters extra parameters for instance workflow
     * @throws InvalidCredentialsException when credentials are invalid
     * @throws InvalidInputException when command is not supported by instance
     */
    void runCommand(Instance instance, String commandName, Map<String, Object> parameters) throws InvalidCredentialsException, InvalidInputException, NotAuthorizedException, ResourceNotFoundException, InstanceBusyException;

    /**
     * Runs a command on instance
     * @param instance valid instance
     * @param commandName name of command/workflow
     * @throws InvalidCredentialsException when credentials are invalid
     * @throws InvalidInputException when command is not supported by instance
     */
    void runCommand(Instance instance, String commandName) throws InvalidCredentialsException, InvalidInputException, NotAuthorizedException, ResourceNotFoundException, InstanceBusyException;

    /**
     * Gets a list of all applications, available for current account
     * @return list of apps, no null
     */
    List<Application> getAllApplications() throws InvalidCredentialsException;

    /**
     * Gets a list of all environments, available for current account
     * @return list of apps, no null
     */
    List<Environment> getAllEnvironments() throws InvalidCredentialsException;
}
