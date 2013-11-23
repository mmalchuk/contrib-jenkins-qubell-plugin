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

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

/**
 * WS wrapper for Qubell service
 * @author Alex Krupnov
 */
public class OrganizationServiceWsImpl extends WebServiceBase implements OrganizationService {

    /**
     * Initializes WS wrapper with configuration
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
            List<Organization> response = new ArrayList<Organization>(client.path("organizations").getCollection(Organization.class));
            return response;

        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();

            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
            }

            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException("User not to list organizations");
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
            List<Application> response = new ArrayList<Application>(client.path("organizations").path(organization.getId()).
                    path("applications")
                    .getCollection(Application.class));
            return response;

        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
        } catch (NotFoundException nfe) {
            throw new ResourceNotFoundException("Specified organization doesn't exist", nfe);
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();
            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
            }

            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException("User not authorized to list applications");
            }

            if (status == 404) {
                throw new ResourceNotFoundException("Specified organization doesn't exist");
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
            List<Environment> response = new ArrayList<Environment>(client.path("organizations").path(organization.getId()).
                    path("environments")
                    .getCollection(Environment.class));
            return response;

        } catch (NotAuthorizedException nae) {
            throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
        } catch (NotFoundException nfe) {
            throw new ResourceNotFoundException("Specified organization doesn't exist", nfe);
        } catch (WebApplicationException e) {
            int status = e.getResponse().getStatus();
            if (status == 401) {
                throw new com.qubell.services.exceptions.InvalidCredentialsException("The specified credentials are not valid");
            }

            if (status == 403) {
                throw new com.qubell.services.exceptions.NotAuthorizedException("User not authorized to list environments");
            }

            if (status == 404) {
                throw new ResourceNotFoundException("Specified organization doesn't exist");
            }

            throw e;
        }
    }
}
