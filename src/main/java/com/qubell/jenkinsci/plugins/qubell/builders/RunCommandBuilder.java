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

package com.qubell.jenkinsci.plugins.qubell.builders;

import com.qubell.jenkinsci.plugins.qubell.JsonParser;
import com.qubell.services.Instance;
import com.qubell.services.InstanceStatusCode;
import com.qubell.services.exceptions.InvalidCredentialsException;
import com.qubell.services.exceptions.InvalidInputException;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.util.FormValidation;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Runs a command on Qubell instance, initialized by {@link StartInstanceBuilder}
 * Waits for instance to reach the Running state and attempts to save return values (if any)
 * @author Alex Krupnov
 */
public class RunCommandBuilder extends QubellBuilder {
    private final String commandName;
    private final String extraParameters;
    private String instanceId;
    private InstanceOptions instanceOptions;

    /**
     * A data bound constructor, executed by Jenkins
     * @param name command name
     * @param extraParameters extended parameters {@link #getExtraParameters()}
     * @param timeout execution timeout {@link #getTimeout()}
     * @param instanceOptions pre-defined instance options see {@link #getInstanceId()}
     * @param outputFilePath path to output file
     * @param failureReaction a target build status which should be set when instnace returns failure status
     */
    @DataBoundConstructor
    public RunCommandBuilder(String name, String extraParameters, String timeout, InstanceOptions instanceOptions, String outputFilePath, String failureReaction) {
        this(name, extraParameters, timeout, instanceOptions, outputFilePath, InstanceStatusCode.RUNNING, failureReaction);
    }


    protected RunCommandBuilder(String name, String extraParameters, String timeout, InstanceOptions instanceOptions, String outputFilePath, InstanceStatusCode expectedStatus, String failureReaction) {
        super(timeout, expectedStatus, outputFilePath, failureReaction);
        this.commandName = name;
        this.extraParameters = extraParameters;
        this.instanceOptions = instanceOptions;
        if(instanceOptions != null){
            this.instanceId = instanceOptions.getInstanceId();
        }

    }

    /**
     * @return Command name, exposed for Jelly UI
     */
    public String getName() {
        return commandName;
    }

    /**
     * Id of pre-defined instance
     * @return value or null
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * A custom, pre defined instance information, if supplied, saved value is ignored
     * @return instance id or null
     */
    public InstanceOptions getInstanceOptions() {
        return instanceOptions;
    }

    /**
     * Gets instance id from build pre-defined value or, if not pre-defined, from persistent container
     * @param build current build
     * @param buildLog build log
     * @return value of instance id
     */
    protected String retrieveInstanceId(AbstractBuild build, PrintStream buildLog) {

        String instanceId = !StringUtils.isBlank(getInstanceId()) ? getInstanceId() : readBuildVariable(build, INSTANCE_ID_KEY, buildLog);

        logMessage(buildLog, "retrieved instance id %s", instanceId);

        return instanceId;
    }



    /**
     * @return extra parameters (json string), exposed for Jelly UI
     */
    public String getExtraParameters() {
        return extraParameters;
    }

    /**
     * Performs a build with following steps
     * <ol>
     *     <li>Attempts to get instance id, saved previously</li>
     *     <li>Launches runs given command on instance</li>
     *     <li>Waits for instance to turn into {@link #expectedStatus} state/li>
     *     <li>Saves the return values of instance if any</li>
     * </ol>
     * If any of steps above failed, fails the job
     * @param build current build
     * @param launcher build launcher
     * @param listener build listener
     * @return true of builder finished successfully, otherwise false
     */
    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) {

        PrintStream buildLog = listener.getLogger();
        if(!validateConfiguration()){
            logMessage(buildLog, "Unable to proceed without configuration. Please check global settings page.");

            build.setResult(Result.FAILURE);
            return false;
        }

        logMessage(buildLog, "Getting  instance id");

        String instanceId = retrieveInstanceId(build, buildLog);
        if (StringUtils.isBlank(instanceId)) {
            logMessage(buildLog, "Unable to run command, instance id is unavailable, instance was not started during the build");
            build.setResult(Result.FAILURE);
            return false;
        }
        Instance instance = new Instance(instanceId);

        logMessage(buildLog, "Running command %s on instance %s.", commandName, instance.getId());

        try {

            getServiceFacade().runCommand(instance, commandName, JsonParser.parseMap(extraParameters));
        } catch (InvalidCredentialsException e) {
            logMessage(buildLog, "Error when running command: invalid credentials");
            build.setResult(Result.FAILURE);
            return false;
        } catch (InvalidInputException e) {
            logMessage(buildLog, "Error when running command: command is not supported by manifest");
            build.setResult(Result.FAILURE);
            return false;
        }

        return waitForExpectedStatus(build, buildLog, instance);
    }

    /**
     * Descriptor for {@link RunCommandBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     * See <tt>src/main/resources/com/qubell/jenkinsci/plugins/qubell/builders/RunCommandBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class RunCommandDescriptor extends BaseDescriptor {
        /**
         * Performs on-the-fly validation of the form field command name
         *
         * @param value This parameter receives the value that the user has typed.
         * @return Indicates the outcome of the validation. This is sent to the browser.
         */
        public FormValidation doCheckName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set a commandName");
            return FormValidation.ok();
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Qubell: Run Command";
        }
    }

}
