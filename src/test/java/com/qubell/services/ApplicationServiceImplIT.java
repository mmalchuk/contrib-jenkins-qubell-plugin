package com.qubell.services;

import com.qubell.jenkinsci.plugins.qubell.Configuration;
import com.qubell.services.exceptions.InvalidCredentialsException;
import com.qubell.services.exceptions.InvalidInputException;
import com.qubell.services.ws.ApplicationServiceWsImpl;
import com.qubell.services.ws.LaunchInstanceResponse;
import com.qubell.services.ws.UpdateManifestResponse;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Alex Krupnov
 * @created 16.07.13 12:22
 */
public class ApplicationServiceImplIT extends BaseServiceIT {

    private final String applicationId = "51fcdbb5e4b06130966ff10f";
    private String superSimpleManifest = "launch:\n" +
            "  steps:\n" +
            "        - destroy:\n" +
            "            action: compute.shrink-all\n" +
            "destroy:\n" +
            "  steps:\n" +
            "        - destroy:\n" +
            "            action: compute.shrink-all";


    @Test
    public void testUpdateManifest() throws Exception {
        ApplicationServiceWsImpl applicationService = new ApplicationServiceWsImpl(getTestConfiguration());

        UpdateManifestResponse response = applicationService.updateManifest(applicationId, superSimpleManifest);

        assertNotNull(response);
    }

    @Test
    public void testLaunchApp() throws Exception
    {
        ApplicationServiceWsImpl applicationService = new ApplicationServiceWsImpl(getTestConfiguration());

        Map<String,Object> parameters = new HashMap<String, Object>();
        Map<String,Object> nestedParams = new HashMap<String, Object>();
        nestedParams.put("nested", 2);

        parameters.put("customParam", "value");

        LaunchInstanceResponse result = applicationService.launch(applicationId, "My Instance", null, null, 300000, parameters);
        assertNotNull(result);
        assertFalse(StringUtils.isBlank(result.getId()));
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testInvalidCredentialsForLaunch() throws Exception
    {
        Configuration testConfiguration = getTestConfiguration();
        ApplicationServiceWsImpl applicationService = new ApplicationServiceWsImpl(new Configuration(testConfiguration.getUrl(), testConfiguration.getLogin(), "wrong password", true, true));

        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("customParam", "value");

        LaunchInstanceResponse result = applicationService.launch("51e44bb6e4b031cbc827cc98", "My Instance", null, null, 300000, parameters);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testInvalidCredentialsForManifestUpdate() throws Exception
    {
        Configuration testConfiguration = getTestConfiguration();
        ApplicationServiceWsImpl applicationService = new ApplicationServiceWsImpl(new Configuration(testConfiguration.getUrl(), testConfiguration.getLogin(), "wrong password", true, true));

        UpdateManifestResponse response = applicationService.updateManifest(applicationId, superSimpleManifest);
    }

    @Test( expected = InvalidInputException.class)
    public void testInvalidDataExceptionWhenUpdatingManifest() throws Exception
    {
        Configuration testConfiguration = getTestConfiguration();
        ApplicationServiceWsImpl applicationService = new ApplicationServiceWsImpl(testConfiguration);

        UpdateManifestResponse response = applicationService.updateManifest(applicationId, "pizzzz");
    }

}
