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
import org.microworld.mangopay.entities.Transfer;
import org.microworld.mangopay.misc.HttpMethod;
import org.microworld.mangopay.services.TransferService;

public class DefaultTransferService implements TransferService {
  private final MangopayConnection connection;

  public DefaultTransferService(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public Transfer create(final Transfer transfer) {
    return connection.queryForObject(Transfer.class, HttpMethod.POST, "/transfers", transfer);
  }

  @Override
  public Transfer get(final String id) {
    return connection.queryForObject(Transfer.class, HttpMethod.GET, "/transfers/{0}", null, id);
  }
}
