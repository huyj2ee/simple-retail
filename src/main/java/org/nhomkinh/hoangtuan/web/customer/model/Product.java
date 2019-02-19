package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.ElementCollection;
import java.util.Set;
import java.util.HashSet;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
  @Id
  private String code;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="origin")
  private ManufactureCountry origin;

  @ElementCollection
  @JoinTable(
    joinColumns = @JoinColumn(name = "prod_id"),
    name="price"
  )
  private Set<Price> prices;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    joinColumns = @JoinColumn(name = "prod_id"),
    inverseJoinColumns = @JoinColumn(name = "unit_id"),
    name="price"
  )
  private Set<ProductUnit> units;


  public Product() {
    this.prices = new HashSet<Price>();
  }

  public abstract String getCode();

  public abstract String getName();

  public abstract String getBrief();

  public abstract String getDescription();

  public abstract String getNote();

  public ManufactureCountry getOrigin() {
    return this.origin;
  }

  public Set<Price> getPrices() {
    return this.prices;
  }

  public abstract void setCode(String code);

  public abstract void setName(String name);

  public abstract void setBrief(String brief);

  public abstract void setDescription(String description);

  public abstract void setNote(String note);

  public void setOrigin(ManufactureCountry origin) {
    this.origin = origin;
  }

  public void setPrices(Set<Price> prices) {
    this.prices.retainAll(prices);
    this.prices.addAll(prices);
  }
}

