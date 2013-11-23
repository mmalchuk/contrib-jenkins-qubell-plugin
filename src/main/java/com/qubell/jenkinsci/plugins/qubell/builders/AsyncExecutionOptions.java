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

import org.kohsuke.stapler.DataBoundConstructor;

public class AsyncExecutionOptions {
    private String jobId;

    @DataBoundConstructor
    public AsyncExecutionOptions(String jobId) {
        this.jobId = jobId;
    }

    /**
     * Identifier for the job, spawned by {@link RunCommandBuilder}, used to pick the job result later
     *
     * @return identifier of the job
     */
    public String getJobId() {
        return jobId;
    }
}
