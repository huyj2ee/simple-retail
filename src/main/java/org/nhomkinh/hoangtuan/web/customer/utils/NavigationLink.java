package org.nhomkinh.hoangtuan.web.customer;


public class NavigationLink {
  private String link;
  private String label;


  public NavigationLink() {
  }

  public NavigationLink(String link, String label) {
    this.link = link;
    this.label = label;
  }

  public String getLink() {
    return this.link;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}

