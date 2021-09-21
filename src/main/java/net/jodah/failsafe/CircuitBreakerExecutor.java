/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package net.jodah.failsafe;

/**
 * A PolicyExecutor that handles failures according to a {@link CircuitBreaker}.
 *
 * @param <R> result type
 * @author Jonathan Halterman
 */
class CircuitBreakerExecutor<R> extends PolicyExecutor<R, CircuitBreaker<R>> {
  CircuitBreakerExecutor(CircuitBreaker<R> circuitBreaker, int policyIndex) {
    super(circuitBreaker, policyIndex);
  }

  @Override
  protected ExecutionResult preExecute() {
    if (policy.allowsExecution()) {
      policy.preExecute();
      return null;
    }
    return ExecutionResult.failure(new CircuitBreakerOpenException(policy));
  }

  @Override
  protected void onSuccess(ExecutionResult result) {
    policy.recordSuccess();
  }

  @Override
  protected ExecutionResult onFailure(AbstractExecution<R> execution, ExecutionResult result) {
    policy.recordExecutionFailure(execution);
    return result;
  }
}
