package com.batch.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface BasicMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(D dto, @MappingTarget E entity);
}
