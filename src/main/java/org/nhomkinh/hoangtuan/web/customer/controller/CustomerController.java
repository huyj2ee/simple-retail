package org.nhomkinh.hoangtuan.web.customer.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.nhomkinh.hoangtuan.web.customer.model.Category;
import org.nhomkinh.hoangtuan.web.customer.model.Product;
import org.nhomkinh.hoangtuan.web.customer.model.Price;
import org.nhomkinh.hoangtuan.web.customer.model.Image;
import org.nhomkinh.hoangtuan.web.customer.model.ProductUnit;
import org.nhomkinh.hoangtuan.web.customer.repository.CategoryRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductUnitRepository;
import java.util.function.Consumer;
import java.util.Collection;


@Controller
public class CustomerController {
  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductUnitRepository productUnitRepository;


  @GetMapping("/categorydbg")
  public String categoryPageDbg(
    @RequestParam(
      value = "lang",
      required = false,
      defaultValue = "VN"
    ) String lang,
    @RequestParam(
      value = "cat",
      required = false,
      defaultValue = ""
    ) String catCode,
    Model model
  ) {
    StringBuffer debugMsg = new StringBuffer();

    Consumer<Category> printCategoryProc = category -> {
      debugMsg.append("\n\t");
      debugMsg.append(category.getId());
      debugMsg.append("\n\t\t");
      debugMsg.append(category.getName());
      if (category.getImage() != null) {
        debugMsg.append("\n\t\t");
        debugMsg.append(category.getImage().getUri());
      }
      else {
        debugMsg.append("\n\t\tNo image");
      }
    };

    Consumer<Product> printProductProc = p -> {
      debugMsg.append("\n\t\t");
      debugMsg.append(p.getCode());
      debugMsg.append("\n\t\t");
      debugMsg.append(p.getName());
      debugMsg.append("\n\t\t");
      debugMsg.append(p.getBrief());
      debugMsg.append("\n\t\t");
      debugMsg.append(p.getDescription());
      debugMsg.append("\n\t\t");
      debugMsg.append((p.getNote() == null ? "[!]" : p.getNote()));
      debugMsg.append("\n\t\t");
      debugMsg.append(p.getOrigin().getCode() + ":" + p.getOrigin().getName());
      for (Image img : p.getImages()) {
        debugMsg.append("\n\t\t\t");
        debugMsg.append(img.getUri());
      }
      for (Price price : p.getPrices()) {
        debugMsg.append("\n\t\t\t");
        debugMsg.append(price.getValue());
        debugMsg.append("\n\t\t\t");
        debugMsg.append("time: " + price.getDate().getTime());
        ProductUnit pu = this.productUnitRepository.findById(price.getUnitId()).orElse(null);
        debugMsg.append("\n\t\t\t");
        debugMsg.append(pu.getId());
        debugMsg.append("\n\t\t\t");
        debugMsg.append(pu.getName());
        debugMsg.append("\n\t\t\t");
        debugMsg.append(pu.getCaption());
        debugMsg.append("\n\t\t\t");
        debugMsg.append(pu.getDescription());
      }
    };


    try {
      Category cat = null;
      try {
        Integer catId = new Integer(catCode);
        cat = this.categoryRepository.findById(catId).orElse(null);
      }
      catch(NumberFormatException e) {
        cat = null;
      }
      if (cat != null) {
        // Print product list if any
        Iterable<Product> products = cat.getProducts();
        debugMsg.append("\nProduct list:");
        products.forEach(printProductProc);

        // Print sub category if any
        debugMsg.append("\nCategory list:");
        cat.getCategories().forEach(printCategoryProc);
      }
      else {
        Collection<Category> categories = this.categoryRepository.findAllTopLevelCategories();
        debugMsg.append("\nRoot category list:");
        categories.forEach(printCategoryProc);
      }
    }
    catch(DataAccessException daoException) {
      debugMsg.append("DataAccessException" + daoException.getMessage());
    }
    model.addAttribute("debugMsg", debugMsg.toString());
    return "category";
  }
}


