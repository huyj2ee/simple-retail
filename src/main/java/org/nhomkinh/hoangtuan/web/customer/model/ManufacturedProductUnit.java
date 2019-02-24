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
public class ManufacturedProductUnit extends ProductUnit {
  @Id
  private int id;

  private int value;

  @ManyToOne(
    optional = false,
    fetch = FetchType.EAGER,
    cascade = {
      CascadeType.REFRESH, CascadeType.DETACH
    }
  )
  @JoinColumn
  private DimensionUnit dimensionUnit;


  @Override
  public int getId() {
    return this.id;
  }

  public int getValue() {
    return this.value;
  }

  public DimensionUnit getDimensionUnit() {
    return this.dimensionUnit;
  }

  @Override
  public String getFullName() {
    return this.getName()
      + " " + this.getValue()
      + " " + this.getDimensionUnit().getName();
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public void setDimensionUnit(DimensionUnit dimensionUnit) {
    this.dimensionUnit = dimensionUnit;
  }
}

