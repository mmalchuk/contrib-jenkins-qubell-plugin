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

import javax.net.ssl.SSLException;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientException;
import java.util.Map;

/**
 * @author Alex Krupnov
 */
public class InstanceServiceWsImpl extends WebServiceBase implements InstanceService {

    public InstanceServiceWsImpl(Configuration configuration) {
        super(configuration);
    }

    public RunCommandResponse runCommand(String instanceId, String commandName, Map<String, Object> parameters) throws InvalidCredentialsException, InvalidInputException, com.qubell.services.exceptions.NotAuthorizedException, ResourceNotFoundException, InstanceBusyException {
        WebClient client = getWebClient();
        RunCommandRequestBuilder builder = new RunCommandRequestBuilder();
        builder.addParameters(parameters);

        try {
            return invoke(HttpMethod.POST, client.path("instances").path(instanceId).path(commandName), builder.getRequest(), RunCommandResponse.class);
        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
        } catch (NotFoundException nfe) {
            throw new ResourceNotFoundException("Specified instance does not exist", nfe);
        } catch (BadRequestException bre) {
            throw new InvalidInputException("Command is not supported", bre);
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();
            if (status == 400) {
                throw new InvalidInputException("Command is not supported", e);
            }
            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
            }

            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException("User not authorized to launch workflow");
            }

            if (status == 404) {
                throw new ResourceNotFoundException("Specified instance does not exist");
            }
            if (status == 409) {
                throw new InstanceBusyException("Not allowed to run workflow currently since another workflow is already running");
            }

            throw e;
        }
    }

    public InstanceStatusResponse getStatus(String instanceId) throws InvalidCredentialsException, ResourceNotFoundException, com.qubell.services.exceptions.NotAuthorizedException {
        WebClient client = getWebClient();

        try {
            return invoke(HttpMethod.GET, client.path("instances").path(instanceId), InstanceStatusResponse.class);
        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
        } catch (NotFoundException nfe) {
            throw new ResourceNotFoundException("Specified instance does not exist", nfe);
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();

            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
            }

            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException("User not authorized to launch workflow");
            }

            if (status == 404) {
                throw new ResourceNotFoundException("Specified instance does not exist");
            }

            throw e;
        }
    }
}
