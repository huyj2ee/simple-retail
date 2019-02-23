package org.nhomkinh.hoangtuan.web.customer.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.nhomkinh.hoangtuan.web.customer.utils.InheritanceDAO;

import org.nhomkinh.hoangtuan.web.customer.model.Product;
import org.nhomkinh.hoangtuan.web.customer.model.ProductVN;
import org.nhomkinh.hoangtuan.web.customer.model.Price;
import org.nhomkinh.hoangtuan.web.customer.model.ManufactureCountry;

import org.nhomkinh.hoangtuan.web.customer.model.Unit;
import org.nhomkinh.hoangtuan.web.customer.model.DimensionUnit;
import org.nhomkinh.hoangtuan.web.customer.model.ProductUnit;
import org.nhomkinh.hoangtuan.web.customer.model.ManufacturedProductUnit;
import org.nhomkinh.hoangtuan.web.customer.model.CustomizedCutProductUnit;
import org.nhomkinh.hoangtuan.web.customer.model.Image;
import org.nhomkinh.hoangtuan.web.customer.model.Category;


import org.nhomkinh.hoangtuan.web.customer.repository.ProductVNRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductENRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ManufactureCountryRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.CategoryRepository;

import org.nhomkinh.hoangtuan.web.customer.repository.UnitRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.DimensionUnitRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ProductUnitRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.ManufacturedProductUnitRepository;
import org.nhomkinh.hoangtuan.web.customer.repository.CustomizedCutProductUnitRepository;
import java.util.Calendar;
import java.sql.Date;


import org.phamsodiep.utils.Language;
import org.phamsodiep.utils.MultiLocaleDAO;


@Controller
public class HtmlController {
  @Autowired
  private ManufactureCountryRepository originRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductUnitRepository productUnitRepository;

  private MultiLocaleDAO<ProductRepository, Product> multiLocaleDAO;

  private InheritanceDAO<UnitRepository, Unit> inheritanceDAO;

  public HtmlController(
    @Autowired ProductVNRepository vnRep,
    @Autowired ProductENRepository enRep,
    @Autowired ManufactureCountryRepository manufactureCountryRepository,
    @Autowired DimensionUnitRepository dimensionUnitRepository,
    @Autowired ProductUnitRepository productUnitRepository,
    @Autowired ManufacturedProductUnitRepository manufacturedProductUnitRepository,
    @Autowired CustomizedCutProductUnitRepository customizedCutProductUnitRepository
  ) {
    ProductRepository[] productRepositories = new ProductRepository[Language.COUNT.ordinal()];
    productRepositories[Language.VN.ordinal()] = vnRep;
    productRepositories[Language.EN.ordinal()] = enRep;
    this.multiLocaleDAO = (MultiLocaleDAO<ProductRepository, Product>)
      MultiLocaleDAO.getInstance(Product.class,productRepositories);

    this.inheritanceDAO = new InheritanceDAO<UnitRepository, Unit>(
      DimensionUnit.class, dimensionUnitRepository,
      ProductUnit.class, productUnitRepository,
      ManufacturedProductUnit.class, manufacturedProductUnitRepository,
      CustomizedCutProductUnit.class, customizedCutProductUnitRepository
    );
  }

  @GetMapping("/homepage")
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
          msg += " " + (product.getOrigin().getImage() == null ? "null" : product.getOrigin().getImage().getUri());
      for (Image img : product.getImages()) {
          msg += "\n\t" + img.getUri();
      }
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
            Image img = new Image();
            img.setUri("http://www.mc.com/img" + i);
            if (i % 2 == 0)
              mc.setImage(img);
            originRepository.save(mc);
        }
      }
    }
    catch(DataAccessException daoException) {
      msg += "DataAccessException_" + daoException.getMessage();
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
            //v.setProduct(p);
            v.setUnitId(8 + j);
            p.getPrices().add(v);

            Image img = new Image();
            img.setUri("https://abc/" + j);
            p.getImages().add(img);
        }
        rep.save(p);
      }
      msg += "Succ";
    }
    catch(DataAccessException daoException) {
      msg += "DataAccessException" + daoException.getMessage();
    }
    model.addAttribute("appName", msg);
    return "index";
  }

  @GetMapping("/cu")
  public String createUnitDataPage(
    @RequestParam(
      value = "lang",
      required = false,
      defaultValue = ""
    ) String lang,
    Model model
  ) {
    int id = 1;
    if (lang.length() == 0) {
      lang = Language.VN.name();
    }
    String msg = "UnitRepository: ";

    UnitRepository ur = this.inheritanceDAO.getRepository(DimensionUnit.class);

    try {
      DimensionUnit u = this.inheritanceDAO.createModelObject(DimensionUnit.class);
      u.setId(id++);
      u.setName("d1");
      ur.save(u);

      u = this.inheritanceDAO.createModelObject(DimensionUnit.class);
      u.setId(id++);
      u.setName("d2");
      ur.save(u);

      u = this.inheritanceDAO.createModelObject(DimensionUnit.class);
      u.setId(id++);
      u.setName("d3");
      u = (DimensionUnit) ur.save(u);

      ProductUnit p = this.inheritanceDAO.createModelObject(ProductUnit.class);
      p.setId(id++);
      p.setName("p1");
      ur.save(p);

      p = this.inheritanceDAO.createModelObject(ProductUnit.class);
      p.setId(id++);
      p.setName("p2");
      ur.save(p);

      p = this.inheritanceDAO.createModelObject(ProductUnit.class);
      p.setId(id++);
      p.setName("p3");
      ur.save(p);

      p = this.inheritanceDAO.createModelObject(ProductUnit.class);
      p.setId(id++);
      p.setName("p4");
      ur.save(p);


      ManufacturedProductUnit mp = this.inheritanceDAO.createModelObject(ManufacturedProductUnit.class);
      mp.setDimensionUnit(u);
      mp.setId(id++);
      mp.setName("mp1");
      mp.setValue(100);
      ur.save(mp);

      mp = this.inheritanceDAO.createModelObject(ManufacturedProductUnit.class);
      mp.setDimensionUnit(u);
      mp.setId(id++);
      mp.setName("mp2");
      mp.setValue(200);
      ur.save(mp);

      mp = this.inheritanceDAO.createModelObject(ManufacturedProductUnit.class);
      mp.setDimensionUnit(u);
      mp.setId(id++);
      mp.setName("mp3");
      mp.setValue(300);
      ur.save(mp);


      CustomizedCutProductUnit cp = this.inheritanceDAO.createModelObject(CustomizedCutProductUnit.class);
      cp.setDimensionUnit(u);
      cp.setId(id++);
      cp.setName("cp1");
      cp.setMinValue(1000);
      cp.setMaxValue(1100);
      ur.save(cp);

      cp = this.inheritanceDAO.createModelObject(CustomizedCutProductUnit.class);
      cp.setDimensionUnit(u);
      cp.setId(id++);
      cp.setName("cp2");
      cp.setMinValue(2000);
      cp.setMaxValue(2200);
      ur.save(cp);

      cp = this.inheritanceDAO.createModelObject(CustomizedCutProductUnit.class);
      cp.setDimensionUnit(u);
      cp.setId(id++);
      cp.setName("cp3");
      cp.setMinValue(3000);
      cp.setMaxValue(3300);
      ur.save(cp);

      cp = this.inheritanceDAO.createModelObject(CustomizedCutProductUnit.class);
      cp.setDimensionUnit(u);
      cp.setId(id++);
      cp.setName("cp4");
      cp.setMinValue(4000);
      cp.setMaxValue(4400);
      ur.save(cp);

      msg += "Succ";
    }
    catch(DataAccessException daoException) {
      msg += "DataAccessException" + daoException.getMessage();
    }
    model.addAttribute("appName", msg);
    return "index";
  }

  @GetMapping("/hoangtuanv")
  public String viewHoangTuanPopulatedDataPage(
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
    String msg = "[";

    try {
      Category cat = this.categoryRepository.findById(10).orElse(null);
      Iterable<Product> products = cat.getProducts();
      for (Product p : products) {
        msg += "\n\t\t";
        msg += p.getCode();
        msg += "\n\t\t";
        msg += p.getName();
        msg += "\n\t\t";
        msg += p.getBrief();
        msg += "\n\t\t";
        msg += p.getDescription();
        msg += "\n\t\t";
        msg += (p.getNote() == null ? "[!]" : p.getNote());
        msg += "\n\t\t";
        msg += p.getOrigin().getCode() + ":" + p.getOrigin().getName();
        for (Image img : p.getImages()) {
          msg += "\n\t\t\t";
          msg += img.getUri();
        }
        for (Price price : p.getPrices()) {
          msg += "\n\t\t\t";
          msg += price.getValue();
          msg += "\n\t\t\t";
          msg += "time: " + price.getDate().getTime();
          ProductUnit pu = this.productUnitRepository.findById(price.getUnitId()).orElse(null);
          msg += "\n\t\t\t\t";
          msg += pu.getId();
          msg += "\n\t\t\t\t";
          msg += pu.getName();
          msg += "\n\t\t\t\t";
          msg += pu.getCaption();
          msg += "\n\t\t\t\t";
          msg += pu.getDescription();
        }
      }
    }
    catch(DataAccessException daoException) {
      msg += "DataAccessException" + daoException.getMessage();
    }
    msg += "]";

    model.addAttribute("appName", msg);
    return "index";    
  }

  @GetMapping("/hoangtuan")
  public String populateHoangTuanDataPage(
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
    String msg = "";

    msg += populateUnitHoangTuanData();
    msg += populateOriginCountryHoangTuanData();
    msg += populateCategoryHoangTuanData();
    msg += populateProductHoangTuanData(lang);


    model.addAttribute("appName", msg);
    return "index";
  }

  private String populateUnitHoangTuanData() {
    String msg = "";
    UnitRepository ur = null;

    try {
      // DimensionUnit
      ur = this.inheritanceDAO.getRepository(DimensionUnit.class);
      DimensionUnit u = this.inheritanceDAO.createModelObject(DimensionUnit.class);
      u.setId(1);
      u.setName("m");
      ur.save(u);

      u = this.inheritanceDAO.createModelObject(DimensionUnit.class);
      u.setId(2);
      u.setName("kg");
      ur.save(u);
      u = (DimensionUnit) ur.findById(1).orElse(null);

      // ProductUnit - dummy
      ur = this.inheritanceDAO.getRepository(ProductUnit.class);
      ProductUnit p = this.inheritanceDAO.createModelObject(ProductUnit.class);
      p.setId(1);
      p.setName("pc");
      ur.save(p);

      p = this.inheritanceDAO.createModelObject(ProductUnit.class);
      p.setId(2);
      p.setName("bar");
      ur.save(p);

      // ManufacturedProductUnit
      ur = this.inheritanceDAO.getRepository(ManufacturedProductUnit.class);
      ManufacturedProductUnit mp = this.inheritanceDAO.createModelObject(ManufacturedProductUnit.class);
      mp.setDimensionUnit(u);
      mp.setId(101);
      mp.setName("bar");
      mp.setValue(4);
      ur.save(mp);

      mp = this.inheritanceDAO.createModelObject(ManufacturedProductUnit.class);
      mp.setDimensionUnit(u);
      mp.setId(102);
      mp.setName("bar");
      mp.setValue(6);
      ur.save(mp);

      mp = this.inheritanceDAO.createModelObject(ManufacturedProductUnit.class);
      mp.setDimensionUnit(u);
      mp.setId(103);
      mp.setName("bar");
      mp.setValue(8);
      ur.save(mp);

      // CustomizedCutProductUnit
      CustomizedCutProductUnit cp = this.inheritanceDAO.createModelObject(CustomizedCutProductUnit.class);
      cp.setDimensionUnit(u);
      cp.setId(201);
      cp.setName("bar");
      cp.setMinValue(1);
      cp.setMaxValue(3);
      ur.save(cp);

      msg += " [Unit->OK]";
    }
    catch(DataAccessException daoException) {
      msg += "DataAccessException@Unit" + daoException.getMessage();
    }

    return msg;
  }

  private String populateOriginCountryHoangTuanData() {
    String[] codes = {
      "vn",
      "tw",
      "cn"
    };
    String[] uris = {
      "flags/vn.png",
      "flags/tw.png",
      "flags/cn.png"
    };
    String msg = "";

    try {
      for(int i = 0; i < codes.length; i++) {
          ManufactureCountry mc = new ManufactureCountry();
          mc.setCode(codes[i]);
          Image img = new Image();
          img.setUri(uris[i]);
          this.originRepository.save(mc);
      }
      msg += " [OriginCountry->OK]";
    }
    catch(DataAccessException daoException) {
      msg += " DataAccessException@OriginCountry " + daoException.getMessage();
    }
    return msg;
  }

  private String populateCategoryHoangTuanData() {
    String[] names = {
      "RF",                // 0. Rod and flats
      "PT",                // 1. Pipes and tubes
      "AC",                // 2. Angles and channels
      "H",                 // 3. Hardware
      "A",                 // 4. Architectural
      "TB",                // 5. Transport and bus body
      "E",                 // 6. Electrical
      "TL",                // 7. Transmission line hardware
      "I",                 // 8. Industrial
      "M",                 // 9. Miscellaneous
      // 0. Rod and flats childs:
      "RR",                // 10. Round Rod
      "FB",                // 11. Flat Bar
      "REF",               // 12. Round Edge Flat
      "HRF",               // 13. Haft Round Flat
      "FCF",               // 14. Flat Coil Form
      "SB",                // 15. Square Bar 
      "RFB",               // 16. Rectangular Flat Bar    
      // 1. Pipes and tubes childs:
      // 2. Angles and channels childs:
      // 3. Hardware childs:
      // 4. Architectural childs:
      // 5. Transport and bus body childs:
      // 6. Electrical childs:
      // 7. Transmission line hardware childs:
      // 8. Industrial childs:
      // 9. Miscellaneous childs:
    };

    Integer[] parents = {
      null,                // 0. Rod and flats
      null,                // 1. Pipes and tubes
      null,                // 2. Angles and channels
      null,                // 3. Hardware
      null,                // 4. Architectural
      null,                // 5. Transport and bus body
      null,                // 6. Electrical
      null,                // 7. Transmission line hardware
      null,                // 8. Industrial
      null,                // 9. Miscellaneous
      0,                   // 10. Round Rod
      0,                   // 11. Flat Bar
      0,                   // 12. Round Edge Flat
      0,                   // 13. Haft Round Flat
      0,                   // 14. Flat Coil Form
      0,                   // 15. Square Bar 
      0,                   // 16. Rectangular Flat Bar 
    };

    String msg = "";

    try {
      for(int i = 0; i < names.length; i++) {
        Category cat = new Category();
        cat.setId(i);
        cat.setName(names[i]);
        Integer parentId = parents[i];
        if (parentId != null) {
          // Load parent
          Category parentCat = this.categoryRepository.findById(parentId).orElse(null);
          // Set parent
          cat.setParentCategory(parentCat);
        }
        this.categoryRepository.save(cat);
      }
      msg += " [Category->OK]";
    }
    catch(DataAccessException daoException) {
      msg += " DataAccessException@Category " + daoException.getMessage();
    }
    return msg;
  }

  private String populateProductHoangTuanData(String lang) {
    String[] productNames = new String[] {
      // product name line by line
      "Round Rod",
      "Round Rod"
    };

    String[] productBrief = new String[] {
      // product brief line by line
      "Round Rod small",
      "Round Rod small",
    };

    String[] productDesc = new String[] {
      // product desc line by line
      "Round Rod small: D=5.00mm, Weight 195GM/3.66M",
      "Round Rod small: D=5.00mm, Weight 195GM/3.66M",
    };



    String[] productCodes = new String[] {
      // product code line by line
      "ht-1149-a",
      "ht-1149-b",
    };
    String[] originIds = new String[] {
      // product orgin line by line
      "tw",
      "cn",
    };
    String[][] productsImages = new String[][] {
      new String[] { // list of images for product 0
        "/products/ht-1149-1.jpg",
        "/products/ht-1149-2.jpg"
      },
      new String[] {
        "/products/ht-1149-1.jpg",
        "/products/ht-1149-2.jpg"
      },
    };

    // ManufacturedProductUnit
    // 101 - bar 4m
    // 102 - bar 6m
    // 103 - bar 8m
    // CustomizedCutProductUnit
    // 201 - bar 1-3m
    int[][][] productsPrices = new int[][][] {
      new int[][] {    // list of prices for product 0
        // price[0] -> product unit, price[1] -> value
        new int[] {101, 1400000},
        new int[] {102, 1600000},
        new int[] {103, 2600000},
        new int[] {201,  420000}
      },
      new int[][] {    // list of prices for product 1
        // price[0] -> product unit, price[1] -> value
        new int[] {101, 1300000},
        new int[] {102, 1500000},
        new int[] {103, 2500000},
        new int[] {201,  400000}
//        new int[] {201,  350000}
      }
    };

    ProductRepository productRepository = this.multiLocaleDAO.getRepository(lang);
    String msg = "";

    try {
      int i;
      int j;
      int n = 0;
      Category cat = this.categoryRepository.findById(10).orElse(null);
      for (i = 0; i < productCodes.length; i++) {
        // Create product
        Product p = (Product)multiLocaleDAO.createModelObject(lang);
        p.setCode(productCodes[i]);
        p.setCategory(cat);
        p.setName(productNames[i]);
        p.setBrief(productBrief[i]);
        p.setDescription(productDesc[i]);
        // Attach product to its orignin object
        ManufactureCountry origin = originRepository.findById(originIds[i]).orElse(null);
        p.setOrigin(origin);
        // Create product images
        String[] productImages = productsImages[i];
        for (j = 0; j < productImages.length; j++) {
          Image img = new Image();
          img.setUri(productImages[j]);
          p.getImages().add(img);
        }
        // Attach product to its unit objects and create price
        int[][] productPrices = productsPrices[i];
        for (j = 0; j < productPrices.length; j++) {
          int[] productPrice = productPrices[j];
          Price price = new Price();
          price.setDate(new Date(100));
          price.setUnitId(productPrice[0]);
          price.setValue(productPrice[1]);
          p.getPrices().add(price);
        }
        productRepository.save(p);
      }


      msg += " [Product->OK]";
    }
    catch(DataAccessException daoException) {
      msg += "DataAccessException@Product" + daoException.getMessage();
    }
    return msg;
  }
}




