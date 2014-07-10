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

package com.qubell.services;

/**
 * Qubell workflow execution step
 * @author Alex Krupnov
 */
public class WorkflowStep {
    private String name;
    private WorkflowStepStatus status;
    private int percentComplete;

    /**
     * Inits step with name, status and percentage
     * @param name step name
     * @param status status of step execution
     * @param percentComplete completed percent amount
     */
    public WorkflowStep(String name, WorkflowStepStatus status, int percentComplete) {
        this.name = name;
        this.status = status;
        this.percentComplete = percentComplete;
    }

    /**
     * Step name
     * @return name, not nullable
     */
    public String getName() {
        return name;
    }

    /**
     * Status of step exectution
     * @return enum value
     */
    public WorkflowStepStatus getStatus() {
        return status;
    }

    /**
     * Completed % of step execution
     * @return percent value
     */
    public int getPercentComplete() {
        return percentComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkflowStep that = (WorkflowStep) o;

        return percentComplete == that.percentComplete
                && name.equals(that.name)
                && status == that.status;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + percentComplete;
        return result;
    }
}
