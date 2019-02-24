package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductUnit implements Unit {
  @Id
  private int id;

  private String name;

  private String caption;

  private String description;


  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  public String getFullName() {
    return this.name;
  }

  public String getCaption() {
    return this.caption;
  }

  public String getDescription() {
    return this.description;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

