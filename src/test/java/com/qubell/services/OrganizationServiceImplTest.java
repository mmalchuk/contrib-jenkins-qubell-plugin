package com.qubell.services;

import com.qubell.services.ws.*;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

public class OrganizationServiceImplTest extends BaseServiceTest {

    private final String orgId = "51e2575ae4b031cbc827cbf1";

    @Test
    public void testGetOrganizations() throws Exception {
        OrganizationServiceWsImpl organizationService = new OrganizationServiceWsImpl(getTestConfiguration());

        List<com.qubell.services.ws.Organization> orgs = organizationService.listOrganizations();

        assertNotNull(orgs);

        assertNotSame(0, orgs.size());
        assertNotNull(orgs.get(0).getId());
        assertNotNull(orgs.get(0).getName());
    }

    @Test
    public void testGetApps() throws Exception {
        OrganizationServiceWsImpl organizationService = new OrganizationServiceWsImpl(getTestConfiguration());
        com.qubell.services.ws.Organization organization = new com.qubell.services.ws.Organization();

        organization.setId(orgId);
        List<com.qubell.services.ws.Application> apps = organizationService.listApplications(organization);

        assertNotNull(apps);

        assertNotSame(0, apps.size());
        assertNotNull(apps.get(0).getId());
        assertNotNull(apps.get(0).getName());
    }

    @Test
    public void testGetEnvs() throws Exception {
        OrganizationServiceWsImpl organizationService = new OrganizationServiceWsImpl(getTestConfiguration());
        com.qubell.services.ws.Organization organization = new com.qubell.services.ws.Organization();

        organization.setId(orgId);
        List<com.qubell.services.ws.Environment> envs = organizationService.listEnvironments(organization);

        assertNotNull(envs);
        assertNotSame(0, envs.size());
        assertNotNull(envs.get(0).getId());
        assertNotNull(envs.get(0).getName());
    }

}
