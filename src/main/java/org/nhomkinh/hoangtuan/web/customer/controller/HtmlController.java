package org.nhomkinh.hoangtuan.web.customer.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.nhomkinh.hoangtuan.web.customer.model.Product;
import org.nhomkinh.hoangtuan.web.customer.model.ProductVN;
import org.nhomkinh.hoangtuan.web.customer.model.Price;
import org.nhomkinh.hoangtuan.web.customer.model.ManufactureCountry;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductVNRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductENRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ManufactureCountryRepository;
import java.util.Calendar;
import java.sql.Date;


import org.phamsodiep.utils.Language;
import org.phamsodiep.utils.MultiLocaleDAO;


@Controller
public class HtmlController {
  @Autowired
  private ManufactureCountryRepository originRepository;

  private MultiLocaleDAO<ProductRepository, Product> multiLocaleDAO;

  public HtmlController(
    @Autowired ProductVNRepository vnRep,
    @Autowired ProductENRepository enRep
  ) {
    ProductRepository[] productRepositories = new ProductRepository[Language.COUNT.ordinal()];
    productRepositories[Language.VN.ordinal()] = vnRep;
    productRepositories[Language.EN.ordinal()] = enRep;
    this.multiLocaleDAO = (MultiLocaleDAO<ProductRepository, Product>)
      MultiLocaleDAO.getInstance(Product.class,productRepositories);
  }

  @GetMapping("/")
  public String homePage(
    @RequestParam(
      value = "lang",
      required = false,
      defaultValue = ""
    ) String lang,
    Model model
  ) {
    if (lang.length() == 0) {
      lang = Language.VN.name();
    }
    ProductRepository rep = this.multiLocaleDAO.getRepository(lang);

    String msg = "[" + lang + rep.toString() + "]";

    Iterable<Product> products = rep.findAll();
    for (Product product : products) {
      msg += product.getCode() + ":";
      msg += "\n\tOrigin -> " + product.getOrigin().getCode();
      for (Price price : product.getPrices()) {
          msg += "\n\t" + price.getDate().getTime();
      }
      msg += "\n";
    }

    Product pro = rep.findByCode("CD008");
    if (pro != null && pro.getCode() != null) {
      msg += "Tim duoc code CD008: restate" + pro.getCode() + "\n";
    }

    model.addAttribute("appName", msg);
    return "index";
  }

  private String populateManufactureCountry() {
    String[] codes = {
      "vn",
      "tw",
      "cn"
    };
    String msg = "";
    // In case there is no ManufactureCountry, populate it
    try {
      Iterable<ManufactureCountry> iter = originRepository.findAll();
      int n = 0;
      for (ManufactureCountry o : iter) {
          n++;
      }
      if (n == 0) {
        msg += "No ManufactureCountry found, popupate some data...\n";
        for(int i = 0; i < codes.length; i++) {
            ManufactureCountry mc = new ManufactureCountry();
            mc.setCode(codes[i]);
            originRepository.save(mc);
        }
      }
    }
    catch(DataAccessException daoException) {
      msg += "DataAccessException_";
    }
    return msg;
  }

  @GetMapping("/populate")
  public String populateDataPage(
    @RequestParam(
      value = "lang",
      required = false,
      defaultValue = ""
    ) String lang,
    Model model
  ) {
    if (lang.length() == 0) {
      lang = Language.VN.name();
    }
    ProductRepository rep = this.multiLocaleDAO.getRepository(lang);

    int day = 1;
    Calendar cal = Calendar.getInstance();
    cal.set(2019, 2, day++);
    String msg = lang + "\n" + rep.toString() + "\n" + populateManufactureCountry();


    String[] ccodes = {
      "vn",
      "vn",
      "cn",
      "cn",
      "cn",
      "tw",
      "vn",
      "tw",
      "tw",
      "tw",
      "tw",
      "cn"
    };

    try {
      int i;
      int j;
      int n = 0;
      for (i = 0; i < 10; i++) {
        Product p = (Product)multiLocaleDAO.createModelObject(lang);

        p.setCode("CD00" + i);
        ManufactureCountry mc = originRepository.findById(ccodes[i]).orElse(null);
        if (i % 2 == 0) {
          n++;
        }
        if (mc != null) {
          p.setOrigin(mc);
        }
        for (j = 0; j < 2; j++) { 
            Price v = new Price();
            v.setDate(new Date(cal.getTime().getTime()));
            cal.set(2019, 2, day++);
            v.setProduct(p);
        }
        rep.save(p);
      }
      msg += "Succ";
    }
    catch(DataAccessException daoException) {
      msg += "DataAccessException";
    }
    model.addAttribute("appName", msg);
    return "index";
  }

}




