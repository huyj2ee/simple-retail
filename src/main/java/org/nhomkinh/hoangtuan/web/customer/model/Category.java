package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.util.Set;
import java.util.HashSet;


@Entity
public class Category {
  @Id
  private int id;

  private String name;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn
  private Image image;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn
  private Category parentCategory;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="parentCategory")
  private Set<Category> categories;

  @OneToMany(
    fetch = FetchType.EAGER,
    cascade = {
      CascadeType.REFRESH,
      CascadeType.DETACH
    }
  )
  @JoinColumn(name = "category_id")
  private Set<Product> products;


  public Category() {
    this.products = new HashSet<Product>();
    this.categories = new HashSet<Category>();
  }

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

  public Set<Category> getCategories() {
    return this.categories;
  }

  public Set<Product> getProducts() {
    return this.products;
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

  public void setCategories(Set<Category> categories) {
    this.categories.retainAll(categories);
    this.categories.addAll(categories);
  }

  public void setProducts(Set<Product> products) {
    this.products.retainAll(products);
    this.products.addAll(products);
  }
}

