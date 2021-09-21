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
 * A policy for handling execution failures.
 * <p>
 * Part of the Failsafe SPI.
 *
 * @param <R> result type
 * @author Jonathan Halterman
 */
public interface Policy<R> {
  /**
   * Returns an {@link PolicyExecutor} capable of performing an execution in the context of a Policy and handling
   * results according to the Policy.
   */
  PolicyExecutor<R, ? extends Policy<R>> toExecutor(int policyIndex);
}
