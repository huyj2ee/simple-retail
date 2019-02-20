package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;


@Entity
public class Category {
  @Id
  private int id;

  private String name;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn
  private Image image;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn
  private Category parentCategory;


  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Image getImage() {
    return this.image;
  }

  public Category getParentCategory() {
    return this.parentCategory;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public void setParentCategory(Category parentCategory) {
    this.parentCategory = parentCategory;
  }
}

