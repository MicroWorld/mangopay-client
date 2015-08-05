/**
 * Copyright (C) 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.implementation;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.PayInApi;
import org.microworld.mangopay.entities.DirectCardPayIn;

public class DefaultPayInApi implements PayInApi {
  private final MangopayConnection connection;

  public DefaultPayInApi(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public DirectCardPayIn createDirectCardPayIn(final DirectCardPayIn directCardPayIn) {
    return connection.queryForObject(DirectCardPayIn.class, HttpMethod.POST, "/payins/card/direct", directCardPayIn);
  }
}
