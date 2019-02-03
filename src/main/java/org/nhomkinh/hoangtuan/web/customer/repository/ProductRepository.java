package org.nhomkinh.hoangtuan.web.customer.repository;


import org.springframework.data.repository.CrudRepository;
import org.nhomkinh.hoangtuan.web.customer.model.Product;


public interface ProductRepository extends CrudRepository<Product, String> {
}

