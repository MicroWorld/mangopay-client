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
import org.microworld.mangopay.TransferApi;
import org.microworld.mangopay.entities.Transfer;

public class DefaultTransferApi implements TransferApi {
  private final MangopayConnection connection;

  public DefaultTransferApi(final MangopayConnection connection) {
    this.connection = connection;
  }

  @Override
  public Transfer create(final Transfer transfer) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Transfer get(final String id) {
    // TODO Auto-generated method stub
    return null;
  }
}
