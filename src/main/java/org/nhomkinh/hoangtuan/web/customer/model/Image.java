package org.nhomkinh.hoangtuan.web.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String uri;


  public int getId() {
    return this.id;
  }

  public String getUri() {
    return this.uri;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}

