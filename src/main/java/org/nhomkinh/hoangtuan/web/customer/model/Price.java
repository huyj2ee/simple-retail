package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import java.sql.Date;


@Embeddable
public class Price {
  @Column
  private int value;

  @Column
  private Date date;

  @ManyToOne(
    optional=false,
    fetch = FetchType.EAGER,
    cascade = {
      CascadeType.REFRESH,
      CascadeType.DETACH
    }
  )
  @JoinColumn(name="unit_id")
  private ProductUnit unit;


  public int getValue() {
    return this.value;
  }

  public Date getDate() {
    return this.date;
  }

  public ProductUnit getUnit() {
    return this.unit;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setUnit(ProductUnit unit) {
    this.unit = unit;
  }
}

