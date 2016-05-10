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
package org.microworld.mangopay.implementation.services;

import org.microworld.mangopay.MangopayConnection;
import org.microworld.mangopay.entities.BankWirePayIn;
import org.microworld.mangopay.entities.DirectCardPayIn;
import org.microworld.mangopay.entities.PayIn;
import org.microworld.mangopay.misc.HttpMethod;
import org.microworld.mangopay.services.PayInService;

public class DefaultPayInService implements PayInService {
  private final MangopayConnection connection;

  public DefaultPayInService(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public PayIn getPayIn(final String id) {
    return connection.queryForObject(PayIn.class, HttpMethod.GET, "/payins/{0}", null, id);
  }

  @Override
  public DirectCardPayIn createDirectCardPayIn(final DirectCardPayIn directCardPayIn) {
    return connection.queryForObject(DirectCardPayIn.class, HttpMethod.POST, "/payins/card/direct", directCardPayIn);
  }

  @Override
  public BankWirePayIn createBankWirePayIn(final BankWirePayIn bankWirePayIn) {
    return connection.queryForObject(BankWirePayIn.class, HttpMethod.POST, "/payins/bankwire/direct", bankWirePayIn);
  }
}
