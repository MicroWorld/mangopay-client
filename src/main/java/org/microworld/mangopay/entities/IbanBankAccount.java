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

import com.google.gson.annotations.SerializedName;

public class IbanBankAccount extends BankAccount {
  @SerializedName("IBAN")
  private final String iban;
  @SerializedName("BIC")
  private final String bic;

  public IbanBankAccount(final String ownerName, final Address ownerAddress, final String iban, final String bic, final String tag) {
    this.type = BankAccountType.IBAN;
    this.ownerName = ownerName;
    this.ownerAddress = ownerAddress;
    this.iban = iban;
    this.bic = bic;
    this.tag = tag;
  }

  public String getIban() {
    return iban;
  }

  public String getBic() {
    return bic;
  }
}
