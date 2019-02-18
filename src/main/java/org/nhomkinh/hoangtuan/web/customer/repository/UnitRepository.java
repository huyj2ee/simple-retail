package org.nhomkinh.hoangtuan.web.customer.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.nhomkinh.hoangtuan.web.customer.model.Unit;


@NoRepositoryBean
public interface UnitRepository<T extends Unit> extends CrudRepository<T, Integer> {
}

