package com.treizer.dao_vs_dto.services.interfaces;

import java.util.List;

// Principal DTO, Insert, Update
public interface ICommonService<T, TI, TU> {

    List<T> get();

    T getById(Long id);

    T add(TI insertDto);

    T update(TU updateDto, Long id);

    T delete(Long id);

}
