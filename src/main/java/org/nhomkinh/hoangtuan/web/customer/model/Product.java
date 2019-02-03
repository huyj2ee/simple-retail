package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;

//import java.util.Set;


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

  //private ManufactureCountry origin;

  //private Dimension dimension;

  //private Collection<Price> prices;

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
}

