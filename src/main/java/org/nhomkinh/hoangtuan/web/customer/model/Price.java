package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.sql.Date;


@Entity
public class Price {
  private int value;

  @Id
  private Date date;

  @ManyToOne
  @JoinColumn(name = "pid", nullable=false)
  private Product product;


  public int getValue() {
    return this.value;
  }

  public Date getDate() {
    return this.date;
  }

  public Product getProduct() {
    return this.product;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setProduct(Product product) {
    this.product = product;
    if (this.product != null) {
      this.product.getPrices().add(this);
    }
  }
}

