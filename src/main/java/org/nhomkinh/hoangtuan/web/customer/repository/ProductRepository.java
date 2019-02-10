package org.nhomkinh.hoangtuan.web.customer.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.nhomkinh.hoangtuan.web.customer.model.Product;


@NoRepositoryBean
public interface ProductRepository<T extends Product> extends CrudRepository<T, String> {
  T findByCode(String code);
}

