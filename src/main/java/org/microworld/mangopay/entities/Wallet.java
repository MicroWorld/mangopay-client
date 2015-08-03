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
package org.microworld.mangopay.entities;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Wallet extends Entity {
  @SerializedName("Owners")
  private final List<String> owners;
  @SerializedName("Currency")
  private final Currency currency;
  @SerializedName("Description")
  private String description;

  public Wallet(final String ownerId, final Currency currency, final String description, final String tag) {
    owners = new ArrayList<>(1);
    owners.add(ownerId);
    this.currency = currency;
    this.description = description;
    setTag(tag);
  }

  public String getOwnerId() {
    return owners.get(0);
  }

  public Currency getCurrency() {
    return currency;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }
}
