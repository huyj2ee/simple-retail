package org.nhomkinh.hoangtuan.web.customer.repository;


import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.nhomkinh.hoangtuan.web.customer.model.Category;


public interface CategoryRepository extends CrudRepository<Category, Integer> {
  @Query("SELECT cat FROM Category cat WHERE cat.parentCategory is null")
  Collection<Category> findAllTopLevelCategories();
}

