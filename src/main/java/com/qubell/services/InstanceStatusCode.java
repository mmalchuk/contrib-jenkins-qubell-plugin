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
 * Status of instance lifecycle
 * @author Alex Krupnov
 */
public enum InstanceStatusCode {
    /**
     * Instance requested
     */
    REQUESTED,
    /**
     * Executing a workflow
     */
    EXECUTING,
    /**
     * Execution of workflow failed,
     */
    FAILED,
    /**
     * Running idle
     */
    RUNNING,
    /**
     * Execution cancelled
     */
    CANCELED,
    /**
     * Instance suspended
     */
    SUSPENDED,
    /**
     * Instance is being destroyed
     */
    DESTROYING,
    /**
     * Instance destroyed
     */
    DESTROYED,

    UNKNOWN
}

