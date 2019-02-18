package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class ManufacturedProductUnit extends ProductUnit {
  @Id
  private int id;

  private int value;


  @Override
  public int getId() {
    return this.id;
  }

  public int getValue() {
    return this.value;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  public void setValue(int value) {
    this.value = value;
  }
}

