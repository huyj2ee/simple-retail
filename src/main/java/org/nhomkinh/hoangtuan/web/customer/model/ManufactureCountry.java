package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;


@Entity
public class ManufactureCountry {
  @Id
  private String code;

  private String name;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn
  private Image image;


  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  public Image getImage() {
    return this.image;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setImage(Image image) {
    this.image = image;
  }
}

