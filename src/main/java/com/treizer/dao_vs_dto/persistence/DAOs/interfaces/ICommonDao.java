package com.treizer.dao_vs_dto.persistence.DAOs.interfaces;

import java.util.List;
import java.util.Optional;

public interface ICommonDao<TEntity> {

    List<TEntity> findAll();

    Optional<TEntity> findById(Long id);

    TEntity save(TEntity entity);

    TEntity update(TEntity entity);

    void delete(TEntity entity);

}
