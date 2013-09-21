package com.qubell.services;

import com.qubell.jenkinsci.plugins.qubell.Configuration;
import com.qubell.services.exceptions.InvalidCredentialsException;
import com.qubell.services.ws.*;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.*;

/**
 * @author Alex Krupnov
 * @created 17.07.13 11:29
 */
public class InstanceServiceWsImplIT extends BaseServiceIT {
    @Test
    public void testRunCommand() throws Exception
    {
        ApplicationServiceWsImpl applicationService = new ApplicationServiceWsImpl(getTestConfiguration());
        InstanceServiceWsImpl instanceService = new InstanceServiceWsImpl(getTestConfiguration());

        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("anotherCustomParam", "value");

        LaunchInstanceResponse result = applicationService.launch("51e44bb6e4b031cbc827cc98", "My Instance", null, null, 300000, null);
        assertNotNull("Unable to run command when instance is null", result);
        assertFalse("Unable to run command when instance is null", StringUtils.isBlank(result.getId()));

        RunCommandResponse runCommandResponse = instanceService.runCommand(result.getId(), "destroy", parameters);

        assertNotNull(runCommandResponse);
        assertFalse(StringUtils.isBlank(runCommandResponse.getId()));
    }

    @Test
    public void testGetStatusWithCurrentWorkflow()  throws Exception
    {
        InstanceStatusResponse status = executeWorkflowAndGetStatus("holdon", getTestConfiguration(),getTestConfiguration());

        assertNotNull(status);
        assertNotNull(status.getApplicationId());
        assertNotNull(status.getEnvironmentId());
        assertNotNull(status.getStatus());
        assertNotNull(status.getWorkflow());
        assertNotNull(status.getWorkflow().getSteps());
        assertTrue(status.getWorkflow().getSteps().size() > 0);
    }

    @Test
    public void testGetStatusWithReturnValues() throws Exception {
        InstanceStatusResponse status = executeWorkflowAndGetStatus("returnstuff", getTestConfiguration(), getTestConfiguration());

        assertNotNull(status);
        assertNotNull(status.getReturnValues());
        assertNotNull(status.getReturnValues().size() > 0);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testInvalidCredentialsForStatus() throws Exception{

        InstanceStatusResponse status = executeWorkflowAndGetStatus("returnstuff", getTestConfiguration(), new Configuration(getTestConfiguration().getUrl(), "invalid", "invalid", true, true));
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testInvalidCredentialsForRunCommand() throws Exception{

        InstanceStatusResponse status = executeWorkflowAndGetStatus("returnstuff", new Configuration(getTestConfiguration().getUrl(), "invalid", "invalid", true, true), getTestConfiguration());
    }


    private InstanceStatusResponse executeWorkflowAndGetStatus(String workflow, Configuration runCommandConfig, Configuration checkStatusConfig) throws Exception {
        ApplicationServiceWsImpl applicationService = new ApplicationServiceWsImpl(getTestConfiguration());
        InstanceServiceWsImpl runCommandInstanceService = new InstanceServiceWsImpl(runCommandConfig);
        InstanceServiceWsImpl checkStatusInstanceService = new InstanceServiceWsImpl(checkStatusConfig);

        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("anotherCustomParam", "value");

        LaunchInstanceResponse result = applicationService.launch("51e44bb6e4b031cbc827cc98", "My Instance", null, null, 300000, null);
        assertNotNull("Unable to run command when instance is null", result);
        assertFalse("Unable to run command when instance is null", StringUtils.isBlank(result.getId()));

        RunCommandResponse runCommandResponse = runCommandInstanceService.runCommand(result.getId(), workflow, parameters);

        assertNotNull(runCommandResponse);
        assertFalse(StringUtils.isBlank(runCommandResponse.getId()));

        Thread.sleep(2000);
        return checkStatusInstanceService.getStatus(result.getId());
    }

}
