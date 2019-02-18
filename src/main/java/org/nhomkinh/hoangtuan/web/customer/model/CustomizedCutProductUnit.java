package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class CustomizedCutProductUnit extends ProductUnit {
  @Id
  private int id;

  private int minValue;

  private int maxValue;


  @Override
  public int getId() {
    return this.id;
  }

  public int getMinValue() {
    return this.minValue;
  }

  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  public void setMinValue(int minValue) {
    this.minValue = minValue;
  }

  public void setMaxValue(int maxValue) {
    this.maxValue = maxValue;
  }
}

