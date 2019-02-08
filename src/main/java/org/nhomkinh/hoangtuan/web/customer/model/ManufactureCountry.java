package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class ManufactureCountry {
  @Id
  private String code;

  private String name;


  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setName(String name) {
    this.name = name;
  }
}

