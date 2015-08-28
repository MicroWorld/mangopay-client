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

public class Address {
  @SerializedName("AddressLine1")
  private String addressLine1;
  @SerializedName("AddressLine2")
  private String addressLine2;
  @SerializedName("City")
  private String city;
  @SerializedName("Region")
  private String region;
  @SerializedName("PostalCode")
  private String postalCode;
  @SerializedName("Country")
  private String country;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (addressLine1 == null ? 0 : addressLine1.hashCode());
    result = prime * result + (addressLine2 == null ? 0 : addressLine2.hashCode());
    result = prime * result + (city == null ? 0 : city.hashCode());
    result = prime * result + (country == null ? 0 : country.hashCode());
    result = prime * result + (postalCode == null ? 0 : postalCode.hashCode());
    result = prime * result + (region == null ? 0 : region.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Address other = (Address) obj;
    if (addressLine1 == null) {
      if (other.addressLine1 != null) {
        return false;
      }
    } else if (!addressLine1.equals(other.addressLine1)) {
      return false;
    }
    if (addressLine2 == null) {
      if (other.addressLine2 != null) {
        return false;
      }
    } else if (!addressLine2.equals(other.addressLine2)) {
      return false;
    }
    if (city == null) {
      if (other.city != null) {
        return false;
      }
    } else if (!city.equals(other.city)) {
      return false;
    }
    if (country == null) {
      if (other.country != null) {
        return false;
      }
    } else if (!country.equals(other.country)) {
      return false;
    }
    if (postalCode == null) {
      if (other.postalCode != null) {
        return false;
      }
    } else if (!postalCode.equals(other.postalCode)) {
      return false;
    }
    if (region == null) {
      if (other.region != null) {
        return false;
      }
    } else if (!region.equals(other.region)) {
      return false;
    }
    return true;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(final String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(final String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(final String region) {
    this.region = region;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(final String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(final String country) {
    this.country = country;
  }
}
