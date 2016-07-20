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
package org.microworld.mangopay.entities.bankaccounts;

import org.microworld.mangopay.entities.Address;
import org.microworld.mangopay.entities.Entity;

import com.google.gson.annotations.SerializedName;

public abstract class BankAccount extends Entity {
  @SerializedName("Type")
  protected BankAccountType type;
  @SerializedName("OwnerName")
  protected String ownerName;
  @SerializedName("OwnerAddress")
  protected Address ownerAddress;
  @SerializedName("UserId")
  protected String userId;
  @SerializedName("Active")
  protected boolean active;

  public BankAccountType getType() {
    return type;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public Address getOwnerAddress() {
    return ownerAddress;
  }

  public String getUserId() {
    return userId;
  }

  public boolean isActive() {
    return active;
  }
}
