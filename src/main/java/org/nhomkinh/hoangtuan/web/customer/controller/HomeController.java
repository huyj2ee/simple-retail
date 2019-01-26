package org.nhomkinh.hoangtuan.web.customer.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class HomeController {
  @GetMapping("/")
  public String homePage(Model model) {
    model.addAttribute("appName", "Nhom Kinh Hoang Tuan");
    return "index";
  }
}

