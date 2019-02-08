package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import java.util.Set;
import java.util.HashSet;


@Entity
public class Product {
  @Id
  private String code;

  private String name;

  private String brief;

  private String description;

  private String note;

  //private Set<Image> images;

  //private Category category;

  //private Set<ProductUnit> unit;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="origin")
  private ManufactureCountry origin;

  //private Dimension dimension;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "product",
    cascade = {CascadeType.ALL}, orphanRemoval = true)
  private Set<Price> prices;


  public Product() {
    this.prices = new HashSet<Price>();
  }

  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  public String getBrief() {
    return this.brief;
  }

  public String getDescription() {
    return this.description;
  }

  public String getNote() {
    return this.note;
  }

  public ManufactureCountry getOrigin() {
    return this.origin;
  }

  public Set<Price> getPrices() {
    return this.prices;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBrief(String brief) {
    this.brief = brief;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public void setOrigin(ManufactureCountry origin) {
    this.origin = origin;
  }

  public void setPrices(Set<Price> prices) {
    this.prices.retainAll(prices);
    this.prices.addAll(prices);
    for (Price price : this.prices) {
      if (price.getProduct() != this) {
        price.setProduct(this);
      }
    }
  }
}

