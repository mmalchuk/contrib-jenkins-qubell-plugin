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

import com.qubell.services.exceptions.*;

import java.util.Map;

/**
 * @author Alex Krupnov
 * @created 17.07.13 11:27
 */
public interface InstanceService {
    RunCommandResponse runCommand(String instanceId, String commandName, Map<String, Object> parameters) throws InvalidCredentialsException, InvalidInputException, NotAuthorizedException, ResourceNotFoundException, InstanceBusyException;
    InstanceStatusResponse getStatus(String instanceId) throws InvalidCredentialsException, ResourceNotFoundException, NotAuthorizedException;
}
