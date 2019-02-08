package org.nhomkinh.hoangtuan.web.customer.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.nhomkinh.hoangtuan.web.customer.model.Product;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductRepository;


@Controller
public class HtmlController {
  @Autowired
  private ProductRepository rep;

  @GetMapping("/")
  public String homePage(Model model) {
    String msg = "";
    Iterable<Product> products = rep.findAll();
    for (Product product : products) {
      msg += product.getCode();
    }
    model.addAttribute("appName", msg);
    return "index";
  }

  @GetMapping("/populate")
  public String populateDataPage(Model model) {
    String msg = "";
    try {
      int i;
      for (i = 0; i < 10; i++) {
          Product p = new Product();
          p.setCode("CD00" + i);
          rep.save(p);
      }
      msg = "Succ";
    }
    catch(DataAccessException daoException) {
      msg = "DataAccessException";
    }
    model.addAttribute("appName", msg);
    return "index";
  }
}


