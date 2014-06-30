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
import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Base descriptor type for {@link QubellBuilder} descendants
 *
 * @author Alex Krupnov
 */
public abstract class BaseDescriptor extends BuildStepDescriptor<Builder> {
    /**
     * Validates timeout value: integer, greater then zero
     *
     * @param value string value of timeout passed from string
     * @return jenkins validation container, see {@link FormValidation}
     * @throws IOException
     * @throws ServletException
     */
    public FormValidation doCheckTimeout(@QueryParameter String value)
            throws IOException, ServletException {

        try {
            int timeout = Integer.parseInt(value);
            if (timeout <= 0) {
                return FormValidation.error("Timeout must be a positive integer");
            }
        } catch (NumberFormatException nfe) {
            return FormValidation.error("Timeout must be a positive integer");
        }

        return FormValidation.ok();
    }

    /**
     * Validates extra parameters value: valid JSON map expression
     *
     * @param value string value of JSON object passed from form
     * @return jenkins validation container, see {@link FormValidation}
     * @throws IOException
     * @throws ServletException
     */
    public FormValidation doCheckExtraParameters(@QueryParameter String value)
            throws IOException, ServletException {
        if (JsonParser.parseMap(value) == null) {
            return FormValidation.error("Unable to parse parameters: invalid json string");
        }

        return FormValidation.ok();
    }

    public ListBoxModel doFillFailureReactionItems() {
        ListBoxModel items = new ListBoxModel();

        items.add("Fail build", Result.FAILURE.toString());
        items.add("Mark build unstable", Result.UNSTABLE.toString());
        items.add("Ignore failure", Result.SUCCESS.toString());

        return items;
    }


    /**
     * Returns true if this task is applicable to the given project.
     *
     * @param aClass type of project
     * @return always true since builders has no restrictions
     */
    public boolean isApplicable(Class<? extends AbstractProject> aClass) {
        return true;
    }
}
