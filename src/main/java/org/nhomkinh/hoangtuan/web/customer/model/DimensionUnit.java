package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class DimensionUnit implements Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String name;


  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }
}

