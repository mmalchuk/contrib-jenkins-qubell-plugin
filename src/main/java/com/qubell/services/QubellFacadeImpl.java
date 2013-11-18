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

import com.qubell.jenkinsci.plugins.qubell.Configuration;
import com.qubell.services.exceptions.*;
import com.qubell.services.toa.ApplicationTOA;
import com.qubell.services.toa.EnvironmentTOA;
import com.qubell.services.toa.InstanceStatusTOA;
import com.qubell.services.ws.*;
import com.qubell.services.ws.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Joining multiple restfull web services, see {@link InstanceService} and {@link ApplicationService}
 * @author Alex Krupnov
 */
public class QubellFacadeImpl implements QubellFacade {
    private Configuration configuration;

    /**
     * Gets an organization service implementation
     * @return instance of org service
     */
    protected OrganizationService getOrganizationService(){
        return new OrganizationServiceWsImpl(configuration);
    }

    /**
     * Instance web service
     * @return instance of service
     */
    protected InstanceService getInstanceService(){
        return new InstanceServiceWsImpl(configuration);
    }

    /**
     * Application Web Service
     * @return instance of service
     */
    protected ApplicationService getApplicationService(){
        return new ApplicationServiceWsImpl(configuration);
    }

    /**
     * A configuration object for Qubell plugin
     * @param configuration instance of configuratio
     */
    public QubellFacadeImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     */
    public Instance launchInstance(InstanceSpecification instanceSpecification, LaunchSettings launchSettings) throws InvalidCredentialsException {
        LaunchInstanceResponse instanceResponse = getApplicationService().launch(
                instanceSpecification.getApplication().getId(),
                instanceSpecification.getInstanceName(),
                instanceSpecification.getVersion(),
                launchSettings.getEnvironment() != null ? launchSettings.getEnvironment().getId() : null,
                launchSettings.getDestroyInterval(),
                launchSettings.getParameters()
        );

        return new Instance(instanceResponse.getId(), instanceSpecification.getInstanceName());
    }

    /**
     * {@inheritDoc}
     */
    public Integer updateManifest(Application application, Manifest manifest) throws InvalidCredentialsException, InvalidInputException {
        return getApplicationService().updateManifest(application.getId(), manifest.getContent()).getVersion();
    }

    /**
     * {@inheritDoc}
     */
    public InstanceStatus getStatus(Instance instance) throws InvalidCredentialsException {
        return new InstanceStatusTOA().fromWsResponse(getInstanceService().getStatus(instance.getId()));
    }

    /**
     * {@inheritDoc}
     */
    public void runCommand(Instance instance, String commandName) throws InvalidCredentialsException, InvalidInputException, NotAuthorizedException, ResourceNotFoundException, InstanceBusyException {
        runCommand(instance, commandName, null);
    }

    public List<Application> getAllApplications() throws InvalidCredentialsException {
        List<Application> applications = new ArrayList<Application>();
        List<Organization> organizations = getOrganizations();

        OrganizationService organizationService = getOrganizationService();

        ApplicationTOA appTOA = new ApplicationTOA();

        for(Organization org : organizations){
            applications.addAll(appTOA.fromWs(organizationService.listApplications(org), org));
        }

        return applications;
    }

    public List<Environment> getAllEnvironments() throws InvalidCredentialsException {
        List<Environment> environments = new ArrayList<Environment>();
        List<Organization> organizations = getOrganizations();

        OrganizationService organizationService = getOrganizationService();

        EnvironmentTOA envTOA = new EnvironmentTOA();

        for(Organization org : organizations){
            environments.addAll(envTOA.fromWs(organizationService.listEnvironments(org), org));
        }

        return environments;
    }

    private List<com.qubell.services.ws.Organization> getOrganizations() throws InvalidCredentialsException {
        return getOrganizationService().listOrganizations();
    }

    /**
     * {@inheritDoc}
     */
    public void runCommand(Instance instance, String commandName, Map<String, Object> parameters) throws InvalidCredentialsException, InvalidInputException, NotAuthorizedException, ResourceNotFoundException, InstanceBusyException {
        getInstanceService().runCommand(instance.getId(), commandName, parameters);
    }
}
