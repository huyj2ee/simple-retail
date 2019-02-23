package org.nhomkinh.hoangtuan.web.customer.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.nhomkinh.hoangtuan.web.customer.NavigationLink;
import org.nhomkinh.hoangtuan.web.customer.model.Category;
import org.nhomkinh.hoangtuan.web.customer.model.Product;
import org.nhomkinh.hoangtuan.web.customer.model.Price;
import org.nhomkinh.hoangtuan.web.customer.model.Image;
import org.nhomkinh.hoangtuan.web.customer.model.ProductUnit;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductVNRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductENRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.CategoryRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductUnitRepository;
import java.util.function.Consumer;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import org.phamsodiep.utils.MultiLocaleDAO;
import org.phamsodiep.utils.Language;


@Controller
public class CustomerController {
  private static final NavigationLink homeLink = new NavigationLink("/category", "Home");

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductUnitRepository productUnitRepository;

  private MultiLocaleDAO<ProductRepository, Product> multiLocaleDAO;


  public CustomerController(
    @Autowired ProductVNRepository vnRep,
    @Autowired ProductENRepository enRep
  ) {
    ProductRepository[] productRepositories = new ProductRepository[Language.COUNT.ordinal()];
    productRepositories[Language.VN.ordinal()] = vnRep;
    productRepositories[Language.EN.ordinal()] = enRep;
    this.multiLocaleDAO = (MultiLocaleDAO<ProductRepository, Product>)
      MultiLocaleDAO.getInstance(Product.class,productRepositories);
  }

  @GetMapping("/category")
  public String categoryPage(
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
    Collection<Category> categories = null;
    Collection<Product> products = null;
    Collection<NavigationLink> links = null;
    try {
      try {
        Integer catId = new Integer(catCode);
        Category cat = this.categoryRepository.findById(catId).orElse(null);
        links = this.retrieveNavigationLinks(cat);
        categories = cat.getCategories();
        products = cat.getProducts();
        if (products.size() == 0) {
          products = null;
        }
      }
      catch(NumberFormatException e) {
        categories = this.categoryRepository.findAllTopLevelCategories();
      }
      if (categories.size() == 0) {
        categories = null;
      }
    }
    catch(DataAccessException daoException) {
      categories = null;
    }
    if (links == null) {
      links = this.retrieveNavigationLinks(null);
    }
    model.addAttribute("links", links);
    model.addAttribute("categories", categories);
    model.addAttribute("products", products);
    return "category";
  }

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
        products.forEach(product -> {
          this.debugTraceProduct(debugMsg, product);
        });

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
    return "categorydbg";
  }

  @GetMapping("/productdbg")
  public String productPageDbg(
    @RequestParam(
      value = "lang",
      required = false,
      defaultValue = "VN"
    ) String lang,
    @RequestParam(
      value = "code",
      required = false,
      defaultValue = ""
    ) String code,
    Model model
  ) {
    StringBuffer debugMsg = new StringBuffer();
    ProductRepository<Product> productRepository = this.multiLocaleDAO.getRepository(lang);

    try {
      Product product = productRepository.findById(code).orElse(null);
      this.debugTraceProduct(debugMsg, product);
    }
    catch(DataAccessException daoException) {
      debugMsg.append("DataAccessException" + daoException.getMessage());
    }

    debugMsg.append("Products:\n");
    model.addAttribute("debugMsg", debugMsg.toString());
    return "productdbg";
  }

  private void debugTraceProduct(StringBuffer debugMsg, Product p) {
    if (p == null) {
      debugMsg.append("not found");
      return;
    }
    // Retrieve systemCategory
    Category sectCat = p.getCategory();
    // Retrieve sectionCategory
    Category sysCat = sectCat.getParentCategory();
    debugMsg.append("\nSystem: ");
    if (sysCat != null) {
      debugMsg.append(sysCat.getName());
    }
    else {
      debugMsg.append("unknown");
    }
    debugMsg.append("\nSection: ");
      debugMsg.append(sectCat.getName());

    debugMsg.append("\n\tCode: ");
    debugMsg.append(p.getCode());
    debugMsg.append("\n\tName: ");
    debugMsg.append(p.getName());
    debugMsg.append("\n\tBrief (Dimention information): ");
    debugMsg.append(p.getBrief());
    debugMsg.append("\n\tOrigin: ");
    debugMsg.append(p.getOrigin().getCode() + "/" + p.getOrigin().getName());
    debugMsg.append("\n\tDescription: ");
    debugMsg.append(p.getDescription());
    debugMsg.append("\n\tDimentions: ");

    for (ProductUnit pu : p.getUnits()) {
        debugMsg.append("[");
        debugMsg.append(pu.getId());
        debugMsg.append("-");
        debugMsg.append(pu.getName());
        debugMsg.append("-");
        debugMsg.append(pu.getCaption());
        debugMsg.append("-");
        debugMsg.append(pu.getDescription());
        debugMsg.append("],  ");
    }

    debugMsg.append("\n\tPrices:");
    for (Price price : p.getPrices()) {
      ProductUnit pu = this.productUnitRepository.findById(price.getUnitId()).orElse(null);
      if (pu != null) {
        debugMsg.append("\n\t\t");
        debugMsg.append(pu.getId());
        debugMsg.append(", ");
        debugMsg.append(pu.getName());
        debugMsg.append(", ");
        debugMsg.append(pu.getCaption());
        debugMsg.append(", ");
        debugMsg.append(pu.getDescription());
        debugMsg.append(": ");
      }
      debugMsg.append(price.getValue());
      debugMsg.append(" at time ");
      debugMsg.append(price.getDate().getTime());
    }

    debugMsg.append("\n\tNote: ");
    debugMsg.append((p.getNote() == null ? "[!]" : p.getNote()));
    debugMsg.append("\n\tSome images:");
    for (Image img : p.getImages()) {
      debugMsg.append("\n\t\t");
      debugMsg.append(img.getUri());
    }
    debugMsg.append("\n");
  }

  private static Collection<NavigationLink> retrieveNavigationLinks(Category category) {
    ArrayList<NavigationLink> result = new ArrayList<NavigationLink>();
    for (
      Category pCategory = category;
      pCategory != null;
      pCategory = pCategory.getParentCategory()
    ) {
      NavigationLink link = new NavigationLink();
      link.setLink("/category?cat=" + pCategory.getId());
      link.setLabel(pCategory.getName());
      result.add(link);
    }
    result.add(homeLink);
    Collections.reverse(result);
    return result;
  }
}


