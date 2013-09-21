package com.qubell.services;

import com.qubell.jenkinsci.plugins.qubell.Configuration;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;

/**
 * @author Alex Krupnov
 * @created 17.07.13 11:29
 */
public class BaseServiceIT {
    protected Configuration getTestConfiguration(){
        boolean enableMessageLogging = true;
        if(enableMessageLogging){
            System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");
            LogManager.getRootLogger().setLevel(Level.INFO);
            LogManager.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));

        }
        return new Configuration("https://secure.dev.qubell.com/","a.krupnov@gmail.com","123123123", true, enableMessageLogging);
    }
}
