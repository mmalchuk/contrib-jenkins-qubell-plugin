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

import com.qubell.jenkinsci.plugins.qubell.Configuration;
import com.qubell.services.exceptions.InstanceBusyException;
import com.qubell.services.exceptions.InvalidCredentialsException;
import com.qubell.services.exceptions.InvalidInputException;
import com.qubell.services.exceptions.ResourceNotFoundException;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.*;
import java.util.Map;

/**
 * {@inheritDoc}
 *
 * @author Alex Krupnov
 */
public class ApplicationServiceWsImpl extends WebServiceBase implements ApplicationService {

    /**
     * Inits service with system configuration
     *
     * @param configuration a plugin config
     */
    public ApplicationServiceWsImpl(Configuration configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc}
     */
    public LaunchInstanceResponse launch(String applicationId, String instanceName, Integer version, String environmentId, long destroyInterval, Map<String, Object> parameters) throws InvalidCredentialsException, ResourceNotFoundException, InvalidInputException, com.qubell.services.exceptions.NotAuthorizedException {
        WebClient client = getWebClient();


        LaunchInstanceRequestBuilder builder = new LaunchInstanceRequestBuilder();
        builder.addDestroyInterval(destroyInterval).
                addVersion(version).
                addInstanceName(instanceName).
                addEnvironmentId(environmentId).
                addParameters(parameters);


        try {
            LaunchInstanceResponse response = invoke(HttpMethod.POST, client.path("applications").path(applicationId).path("launch"),
                    builder.getRequest(), LaunchInstanceResponse.class);
            return response;

        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
        } catch (NotFoundException nfe) {
            throw new ResourceNotFoundException("Specified application does not exist", nfe);
        } catch (BadRequestException bre) {
            throw new InvalidInputException("Requested manifest version does not exist", bre);
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();
            if (status == 400) {
                throw new InvalidInputException("Requested manifest version does not exist", e);
            }
            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
            }

            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException("User is not authorized to access the application");
            }

            if (status == 404) {
                throw new ResourceNotFoundException("Specified application does not exist");
            }

            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    public UpdateManifestResponse updateManifest(String applicationId, String manifest) throws InvalidCredentialsException, InvalidInputException, ResourceNotFoundException, com.qubell.services.exceptions.NotAuthorizedException {
        WebClient client = getWebClient();
        client.header("Content-Type", "application/x-yaml");
        try {
            UpdateManifestResponse response = invoke(HttpMethod.PUT, client.path("applications").path(applicationId).path("manifest"),
                    manifest, UpdateManifestResponse.class
            );

            return response;
        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
        } catch (NotFoundException nfe) {
            throw new ResourceNotFoundException("Specified application does not exist", nfe);
        } catch (BadRequestException bre) {
            throw new InvalidInputException("Manifest is invalid", bre);
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();
            if (status == 400) {
                throw new InvalidInputException("Manifest is invalid", e);
            }
            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
            }

            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException("User is not authorized to access the application");
            }

            if (status == 404) {
                throw new ResourceNotFoundException("Specified application does not exist");
            }

            throw e;
        }
    }
}
