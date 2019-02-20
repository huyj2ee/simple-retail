package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;


@Entity
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class CustomizedCutProductUnit extends ProductUnit {
  @Id
  private int id;

  private int minValue;

  private int maxValue;

  @ManyToOne(
    optional=false,
    fetch = FetchType.EAGER,
    cascade = {
      CascadeType.REFRESH,
      CascadeType.DETACH
    }
  )
  @JoinColumn
  private DimensionUnit dimensionUnit;


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

  public DimensionUnit getDimensionUnit() {
    return this.dimensionUnit;
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

  public void setDimensionUnit(DimensionUnit dimensionUnit) {
    this.dimensionUnit = dimensionUnit;
  }
}

