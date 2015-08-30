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

import org.microworld.mangopay.entities.kyc.KycLevel;

import com.google.gson.annotations.SerializedName;

public abstract class User extends Entity {
  @SerializedName("Email")
  private String email;
  @SerializedName("PersonType")
  private PersonType personType;
  @SerializedName("KYCLevel")
  private KycLevel kycLevel;

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public PersonType getPersonType() {
    return personType;
  }

  public KycLevel getKycLevel() {
    return kycLevel;
  }
}
