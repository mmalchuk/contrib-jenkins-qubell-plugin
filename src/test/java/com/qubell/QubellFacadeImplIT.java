package com.qubell;

import com.qubell.services.*;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

/**
 * @author Alex Krupnov
 * @created 17.07.13 15:15
 */
public class QubellFacadeImplIT extends BaseServiceIT {
    private String appManifest = "launch:\n" +
            "  steps:\n" +
            "        - destroy:\n" +
            "            action: compute.shrink-all\n" +
            "returnstuff:\n" +
            "  steps:\n" +
            "        - awaitnothing:\n" +
            "            action: wait\n" +
            "            parameters:\n" +
            "              delay: 0\n" +
            "\n" +
            "  return:\n" +
            "      value1:\n" +
            "          description: Sample ReturnParam\n" +
            "          value: \"valueOfValue1\"\n" +
            "      value2:\n" +
            "          description: Sample Return Param 2\n" +
            "          value: \"valueOfValue2\"\n" +
            "holdon:\n" +
            "  steps:\n" +
            "        - await60seconds:\n" +
            "            action: wait\n" +
            "            parameters:\n" +
            "              delay: 60\n" +
            "\n" +
            "destroy:\n" +
            "  steps:\n" +
            "        - destroy:\n" +
            "            action: compute.shrink-all";
    private String applicationId = "52000761e4b0b2a404bf029f";

    @Test
    public void testGetAllApps() throws Exception{
        QubellFacadeImpl facade = new QubellFacadeImpl(getTestConfiguration());
        List<Application> apps = facade.getAllApplications();

        assertNotNull(apps);
        assertNotSame(0, apps.size());
    }

    @Test
    public void testGetAllEnvs() throws Exception{
        QubellFacadeImpl facade = new QubellFacadeImpl(getTestConfiguration());
        List<Environment> envs = facade.getAllEnvironments();

        assertNotNull(envs);
        assertNotSame(0, envs.size());
    }


    @Test
    public void testPluginFlow() throws Exception{
        QubellFacadeImpl facade = new QubellFacadeImpl(getTestConfiguration());

        Application application = new Application(applicationId);

        Integer version = facade.updateManifest(application, new Manifest(appManifest));

        assertNotNull(version);

        Map<String, Object> myParams = new HashMap<String, Object>();
        myParams.put("param1", "val1");
        myParams.put("param2", "val2");


        Instance instance = facade.launchInstance(
                new InstanceSpecification(application, "test instance", version),
                new LaunchSettings(myParams)
                );

        assertNotNull(instance);

        Map<String, Object> commandParams = new HashMap<String, Object>();
        Map<String, Object> nestedParams = new HashMap<String, Object>();
        nestedParams.put("nested1", "nested");
        commandParams.put("one", "1");
        commandParams.put("two", "2");
        //commandParams.put("nested", nestedParams);

        facade.runCommand(instance, "returnstuff", commandParams);

        Thread.sleep(2000);
        InstanceStatus status = facade.getStatus(instance);

        facade.runCommand(instance, "holdon", commandParams);

        status = facade.getStatus(instance);

        facade.runCommand(instance, "destroy");
    }
}
