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
import com.qubell.services.exceptions.InvalidCredentialsException;
import com.qubell.services.exceptions.ResourceNotFoundException;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * WS wrapper for Qubell service
 *
 * @author Alex Krupnov
 */
public class OrganizationServiceWsImpl extends WebServiceBase implements OrganizationService {

    /**
     * Initializes WS wrapper with configuration
     *
     * @param configuration plugin configuration
     */
    public OrganizationServiceWsImpl(Configuration configuration) {
        super(configuration);
    }

    /**
     * {@inheritDoc}
     */
    public List<Organization> listOrganizations() throws InvalidCredentialsException, com.qubell.services.exceptions.NotAuthorizedException {
        WebClient client = getWebClient();

        try {
            return new ArrayList<Organization>(invokeAndGetCollection(
                    HttpMethod.GET,
                    client.path("organizations"),
                    null,
                    Organization.class
            ));
        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException(
                    parseJsonErrorMessage(nae.getResponse(), "The specified credentials are not valid"),
                    nae
            );
        } catch (WebApplicationException e) {
            Response response = e.getResponse();
            int status = response.getStatus();

            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException(
                        parseJsonErrorMessage(response, "The specified credentials are not valid"),
                        e
                );
            }
            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException(
                        parseJsonErrorMessage(response, "User not authorized to list organizations"),
                        e
                );
            }
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Application> listApplications(Organization organization) throws InvalidCredentialsException, ResourceNotFoundException, com.qubell.services.exceptions.NotAuthorizedException {
        WebClient client = getWebClient();

        try {
            return new ArrayList<Application>(invokeAndGetCollection(
                    HttpMethod.GET,
                    client.path("organizations").path(organization.getId()).
                    path("applications"),
                    null,
                    Application.class
            ));

        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException(
                    parseJsonErrorMessage(nae.getResponse(), "The specified credentials are not valid"),
                    nae
            );
        } catch (NotFoundException nfe) {
            throw new ResourceNotFoundException(
                    parseJsonErrorMessage(nfe.getResponse(), "Specified organization doesn't exist"),
                    nfe
            );
        } catch (WebApplicationException e) {
            Response response = e.getResponse();
            int status = response.getStatus();
            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException(
                        parseJsonErrorMessage(e.getResponse(), "The specified credentials are not valid"),
                        e
                );
            }
            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException(
                        parseJsonErrorMessage(e.getResponse(), "User not authorized to list applications"),
                        e
                );
            }
            if (status == 404) {
                throw new ResourceNotFoundException(
                        parseJsonErrorMessage(e.getResponse(), "Specified organization doesn't exist"),
                        e
                );
            }

            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Environment> listEnvironments(Organization organization) throws InvalidCredentialsException, ResourceNotFoundException, com.qubell.services.exceptions.NotAuthorizedException {
        WebClient client = getWebClient();

        try {
            return new ArrayList<Environment>(invokeAndGetCollection(
                    HttpMethod.GET,
                    client.path("organizations").path(organization.getId()).
                    path("environments"),
                    null,
                    Environment.class
            ));

        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException(
                    parseJsonErrorMessage(nae.getResponse(), "The specified credentials are not valid"),
                    nae
            );
        } catch (NotFoundException nfe) {
            throw new ResourceNotFoundException(
                    parseJsonErrorMessage(nfe.getResponse(), "Specified organization doesn't exist"),
                    nfe
            );
        } catch (WebApplicationException e) {
            Response response = e.getResponse();
            int status = response.getStatus();
            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException(
                        parseJsonErrorMessage(response, "The specified credentials are not valid"),
                        e
                );
            }
            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException(
                        parseJsonErrorMessage(e.getResponse(), "User not authorized to list environments"),
                        e
                );
            }

            if (status == 404) {
                throw new ResourceNotFoundException(
                        parseJsonErrorMessage(e.getResponse(), "Specified organization doesn't exist"),
                        e
                );
            }

            throw e;
        }
    }
}
