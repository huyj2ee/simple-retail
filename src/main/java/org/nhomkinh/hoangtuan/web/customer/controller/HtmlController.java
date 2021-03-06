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


//////import org.phamsodiep.utils.Language;
//////import org.phamsodiep.utils.MultiLocaleDAO;


@Controller
public class HtmlController {
  @Autowired
  private ManufactureCountryRepository originRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductUnitRepository productUnitRepository;

//////  private MultiLocaleDAO<ProductRepository, Product> multiLocaleDAO;
  private ProductRepository[] productRepositories;

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
//////    ProductRepository[] productRepositories = new ProductRepository[Language.COUNT.ordinal()];
//////    productRepositories[Language.VN.ordinal()] = vnRep;
//////    productRepositories[Language.EN.ordinal()] = enRep;
//////    this.multiLocaleDAO = (MultiLocaleDAO<ProductRepository, Product>)
//////      MultiLocaleDAO.getInstance(Product.class,productRepositories);

    productRepositories = new ProductRepository[2];
    productRepositories[0] = vnRep;
    productRepositories[1] = enRep;

    this.inheritanceDAO = new InheritanceDAO<UnitRepository, Unit>(
      DimensionUnit.class, dimensionUnitRepository,
      ProductUnit.class, productUnitRepository,
      ManufacturedProductUnit.class, manufacturedProductUnitRepository,
      CustomizedCutProductUnit.class, customizedCutProductUnitRepository
    );
  }

/*  @GetMapping("/homepage")
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
  }*/

  /*@GetMapping("/populate")
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
  }*/

  @GetMapping("/hoangtuan")
  public String populateHoangTuanDataPage(
    @RequestParam(
      value = "password",
      required = false,
      defaultValue = ""
    ) String password,
    Model model
  ) {
    if (password.length() <= 5) {
      model.addAttribute("appName", "Your password is too short!");
      return "index";
    }
    if (password.compareTo("123456") != 0) {
      model.addAttribute("appName", "Wrong password!");
      return "index";
    }
    String msg = "";

    msg += populateUnitHoangTuanData();
    msg += populateOriginCountryHoangTuanData();
    msg += populateCategoryHoangTuanData();
    msg += populateProductHoangTuanData("VN");

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
      p.setName("cái");
      ur.save(p);

      p = this.inheritanceDAO.createModelObject(ProductUnit.class);
      p.setId(2);
      p.setName("thanh");
      ur.save(p);

      // ManufacturedProductUnit
      ur = this.inheritanceDAO.getRepository(ManufacturedProductUnit.class);
      ManufacturedProductUnit mp = this.inheritanceDAO.createModelObject(ManufacturedProductUnit.class);
      mp.setDimensionUnit(u);
      mp.setId(101);
      mp.setName("thanh");
      mp.setValue(4);
      ur.save(mp);

      mp = this.inheritanceDAO.createModelObject(ManufacturedProductUnit.class);
      mp.setDimensionUnit(u);
      mp.setId(102);
      mp.setName("thanh");
      mp.setValue(6);
      ur.save(mp);

      mp = this.inheritanceDAO.createModelObject(ManufacturedProductUnit.class);
      mp.setDimensionUnit(u);
      mp.setId(103);
      mp.setName("thanh");
      mp.setValue(8);
      ur.save(mp);

      // CustomizedCutProductUnit
      CustomizedCutProductUnit cp = this.inheritanceDAO.createModelObject(CustomizedCutProductUnit.class);
      cp.setDimensionUnit(u);
      cp.setId(201);
      cp.setName("thanh");
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

  private String populateUnitHoangTuanData2() {
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
    //////
    String[] names = {
      "Việt Nam",
      "Đài Loan",
      "Trung Quốc"
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
          mc.setName(names[i]);
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
      "Rod and flats",              // 0. Rod and flats
      "Pipes and tubes",            // 1. Pipes and tubes
      "Angles and channels",        // 2. Angles and channels
      "Hardware",                   // 3. Hardware
      "Architectural",              // 4. Architectural
      "Transport and bus body",     // 5. Transport and bus body
      "Electrical",                 // 6. Electrical
      "Transmission line hardware", // 7. Transmission line hardware
      "Industrial",                 // 8. Industrial
      "Miscellaneous",              // 9. Miscellaneous
      // 0. Rod and flats childs:
      "Round Rod",                  // 10. Round Rod
      "Flat Bar",                   // 11. Flat Bar
      "Round Edge Flat",            // 12. Round Edge Flat
      "Haft Round Flat",            // 13. Haft Round Flat
      "Flat Coil Form",             // 14. Flat Coil Form
      "Square Bar",                 // 15. Square Bar
      "Rectangular Flat Bar",       // 16. Rectangular Flat Bar
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

  private String populateCategoryHoangTuanData2() {
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
      "Round Rod small",
      "Round Rod small"
    };

    String[] productBrief = new String[] {
      // product brief line by line
      "D=5.00mm, Weight 195GM/3.66M",
      "D=5.00mm, Weight 195GM/3.66M",
    };

    String[] productDesc = new String[] {
      // product desc line by line
      "Sản phẩm chất lượng tốt của công ty Yuan Sheng chất lượng hơn hẳn sản phẩm mua ở chợ Dân Sinh",
      "Sản phẩm mới, hợp kim nhôm chịu nhiệt cao lên tới 500oC mà vẫn chưa bị nóng chảy. Dùng trong hàng hãi và vũ trụ. Hàng xách tay nên vui lòng gọi điện trước khi đến, để biết tình trạng còn hàng.",
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
    String msg = "";
    ProductUnit productUnit101 = null;
    ProductUnit productUnit102 = null;
    ProductUnit productUnit103 = null;
    ProductUnit productUnit201 = null;

    try {
      productUnit101 = this.productUnitRepository.findById(101).orElse(null);
      productUnit102 = this.productUnitRepository.findById(102).orElse(null);
      productUnit103 = this.productUnitRepository.findById(103).orElse(null);
      productUnit201 = this.productUnitRepository.findById(201).orElse(null);
    }
    catch(Exception e) {
      msg += e.getMessage() + "\n";
    }

    Object[][][] productsPrices = new Object[][][] {
      new Object[][] {    // list of prices for product 0
        // price[0] -> product unit, price[1] -> value
        new Object[] {productUnit101, 1400000},
        new Object[] {productUnit102, 1600000},
        new Object[] {productUnit103, 2600000},
        new Object[] {productUnit201,  420000}
      },
      new Object[][] {    // list of prices for product 1
        // price[0] -> product unit, price[1] -> value
        new Object[] {productUnit101, 1300000},
        new Object[] {productUnit102, 1500000},
        new Object[] {productUnit103, 2500000},
        new Object[] {productUnit201,  400000}
      }
    };

    //////ProductRepository productRepository = this.multiLocaleDAO.getRepository(lang);
    ProductRepository productRepository = productRepositories[0];

    try {
      int i;
      int j;
      int n = 0;
      Category cat = this.categoryRepository.findById(10).orElse(null);
      for (i = 0; i < productCodes.length; i++) {
        // Create product
        //////Product p = (Product)multiLocaleDAO.createModelObject(lang);
        Product p = new ProductVN();
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
        Object[][] productPrices = productsPrices[i];
        for (j = 0; j < productPrices.length; j++) {
          try {
            Object[] productPrice = productPrices[j];
            Price price = new Price();
            price.setDate(new Date(100));
            price.setUnit((ProductUnit) productPrice[0]);
            price.setValue((Integer) productPrice[1]);
            p.getPrices().add(price);
          }
          catch(Exception e) {
            msg += e.getMessage() + "\n";
          }
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

  /*private String populateManufactureCountry() {
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
  }*/
}


