package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Date;


@Embeddable
public class Price {
  @Column
  private int value;

  @Column
  private Date date;

  @Column(name="unit_id")
  private int unitId;


  public int getUnitId() {
    return this.unitId;
  }

  public void setUnitId(int unitId) {
    this.unitId = unitId;
  }

  public int getValue() {
    return this.value;
  }

  public Date getDate() {
    return this.date;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}

