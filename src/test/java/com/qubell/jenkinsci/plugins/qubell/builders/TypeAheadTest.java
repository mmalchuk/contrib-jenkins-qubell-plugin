package com.qubell.jenkinsci.plugins.qubell.builders;

import com.qubell.jenkinsci.plugins.qubell.JsonParser;
import com.qubell.services.BaseServiceIT;
import com.qubell.services.QubellFacade;
import com.qubell.services.QubellFacadeImpl;
import org.junit.Assert;
import org.junit.Test;

public class TypeAheadTest extends BaseServiceIT {

    @Test
    public void TestSerializeApps() throws Exception {
        QubellFacade facade = new QubellFacadeImpl(getTestConfiguration());

        String json = JsonParser.serialize(facade.getAllApplications());

        Assert.assertNotNull(json);
    }

    @Test
    public void testSerializeEnvironments() throws Exception{
        QubellFacade facade = new QubellFacadeImpl(getTestConfiguration());

        String json = JsonParser.serialize(facade.getAllEnvironments());

        Assert.assertNotNull(json);

    }
}
