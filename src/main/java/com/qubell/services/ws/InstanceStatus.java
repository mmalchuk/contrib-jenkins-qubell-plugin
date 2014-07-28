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

package com.qubell.services.ws;

import javax.xml.bind.annotation.XmlEnum;

/**
 * WS instance status, see {@link com.qubell.services.InstanceStatusCode} for explanation
 * @author Alex Krupnov
 */
@XmlEnum
public enum InstanceStatus {
    Requested,
    Executing,
    Failed,
    Running,
    Canceled,
    Suspended,
    Destroying,
    Destroyed,
    Unknown
}
