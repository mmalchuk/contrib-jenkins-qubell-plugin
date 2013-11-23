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
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.util.FormValidation;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents a build step, which is waiting for the result of previously started asynchronous job
 *
 * @author Alex Krupnov
 */
public class ResultWaitBuilder extends QubellBuilder {
    private final String jobId;
    private String jobIdResolved;

    @DataBoundConstructor
    public ResultWaitBuilder(String jobId, String timeout, String failureReaction) {
        super(timeout, InstanceStatusCode.RUNNING, null, failureReaction);

        this.jobId = jobId;
    }

    /**
     * Performs a build with following steps
     * <ol>
     * <li>Picks information about previously started job</li>
     * <li>Waits for expected instance status</li>
     * </ol>
     * If any of steps above failed, fails the job, marks build unstable or ignores the result
     *
     * @param build    current build
     * @param launcher build launcher
     * @param listener build listener
     * @return true of builder finished successfully, otherwise false
     */
    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
        resolveParameterPlaceholders(build, listener);

        PrintStream buildLog = listener.getLogger();
        Map<String, Object> jobInfo = new HashMap<String, Object>();

        try {
            jobInfo = JsonParser.parseMap(build.getWorkspace().child(jobIdResolved).readToString());
        } catch (IOException e) {

            logMessage(buildLog, "Unable to read job file %s", e.getMessage());
            build.setResult(Result.FAILURE);
            return false;
        }
        outputFilePath = jobInfo.get(ASYNC_OUTPUT_PATH_KEY).toString();
        expectedStatus = InstanceStatusCode.valueOf(jobInfo.get(ASYNC_EXPECTED_STATUS_KEY).toString());

        //After variables are recovered and set, they need to be filtered against placeholders
        resolveParameterPlaceholders(build, listener);

        Instance instance = new Instance(jobInfo.get(ASYNC_INSTANCE_ID_KEY).toString());
        logMessage(buildLog, "Retrieved async job settings, instance id %s, expected status %s, output path %s", instance.getId(), expectedStatus, outputFilePath);

        return waitForExpectedStatus(build, buildLog, instance);
    }

    @Override
    protected void resolveParameterPlaceholders(AbstractBuild build, BuildListener listener) throws IOException, InterruptedException {
        super.resolveParameterPlaceholders(build, listener);

        this.jobIdResolved = resolveVariableMacros(build, listener, this.jobId);
    }

    /**
     * Job id to wait for
     *
     * @return id of the job
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Descriptor for {@link ResultWaitBuilder}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     * See <tt>src/main/resources/com/qubell/jenkinsci/plugins/qubell/builders/RunCommandBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class ResultWaitDescriptor extends BaseDescriptor {
        /**
         * Performs on-the-fly validation of the form field command name
         *
         * @param value This parameter receives the value that the user has typed.
         * @return Indicates the outcome of the validation. This is sent to the browser.
         */
        public FormValidation doCheckJobId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error("Please set a job id");
            return FormValidation.ok();
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Qubell: Wait for job completion";
        }
    }

}
