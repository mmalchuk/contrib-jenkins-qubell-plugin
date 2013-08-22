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

import com.qubell.services.exceptions.InvalidCredentialsException;

import java.util.List;

/**
 * Responsible for organization specific operations
 * @author Alex Krupnov
 */

public interface OrganizationService {

    /**
     * Lists Qubell organizations for given account
     * @return not empty list of organizations
     */
    List<Organization> listOrganizations() throws InvalidCredentialsException;

    /**
     * Returns list of applications for given organization
     * @param organization organization to use
     * @return list of apps
     */
    List<Application> listApplications(Organization organization) throws InvalidCredentialsException;

    /**
     * Returns list of environments for given organization
     * @param organization organization to use
     * @return list of environments
     */
    List<Environment> listEnvironments(Organization organization) throws InvalidCredentialsException;
}
