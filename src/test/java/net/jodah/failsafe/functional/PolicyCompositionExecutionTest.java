/*
 * Copyright 2021 the original author or authors.
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
package net.jodah.failsafe.functional;

import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Execution;
import net.jodah.failsafe.RetryPolicy;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Tests the handling of ordered policy execution via an Execution or AsyncExecution.
 */
@Test
public class PolicyCompositionExecutionTest {
  public void testRetryPolicyThenCircuitBreaker() {
    RetryPolicy<Object> rp = new RetryPolicy<>().withMaxRetries(2);
    CircuitBreaker<Object> cb = new CircuitBreaker<>().withFailureThreshold(5);

    Execution<Object> execution = new Execution<>(rp, cb);
    execution.recordFailure(new Exception());
    execution.recordFailure(new Exception());
    assertFalse(execution.isComplete());
    execution.recordFailure(new Exception());
    assertTrue(execution.isComplete());

    assertTrue(cb.isClosed());
  }

  public void testCircuitBreakerThenRetryPolicy() {
    RetryPolicy<Object> rp = new RetryPolicy<>().withMaxRetries(1);
    CircuitBreaker<Object> cb = new CircuitBreaker<>().withFailureThreshold(5);

    Execution<Object> execution = new Execution<>(cb, rp);
    execution.recordFailure(new Exception());
    assertFalse(execution.isComplete());
    execution.recordFailure(new Exception());
    assertTrue(execution.isComplete());

    assertTrue(cb.isClosed());
  }
}
