package com.ev.momcalcboot.dtoService;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.dto.BoltDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BoltDtoService {
    abstract BoltDto toDto(BoltEntity bolt);
}
