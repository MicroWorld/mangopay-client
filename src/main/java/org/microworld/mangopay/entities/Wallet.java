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
