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

package com.qubell.services.toa;

import com.qubell.services.Application;
import com.qubell.services.ws.Organization;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for assemble and dissemble of {@link com.qubell.services.Application} object
 * @author Alex Krupnov
 */
public class ApplicationTOA {

    /**
     * Constructs app from ws data
     * @param wsApp ws app to use
     * @param wsOrg ws organization to use
     * @return app instance
     */
    public Application fromWs(com.qubell.services.ws.Application wsApp, Organization wsOrg){
        return new Application(wsApp.getId(), wsApp.getName(), (new OrganizationTOA()).fromWs(wsOrg));
    }

    /**
     * Converts a list of ws apps to list of apps
     * @param wsApps ws apps list
     * @param wsOrg ws org
     * @return a not null list of apps
     */
    public List<Application> fromWs(List<com.qubell.services.ws.Application> wsApps, Organization wsOrg){
        List<Application> apps = new ArrayList<Application>();

        for (com.qubell.services.ws.Application wsApp : wsApps){
            apps.add(fromWs(wsApp, wsOrg));
        }

        return apps;
    }
}
