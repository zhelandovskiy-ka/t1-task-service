package com.zhelandovskiy.t1_aop_1.mapper;

import com.zhelandovskiy.t1_aop_1.dto.TaskCreateUpdateDto;
import com.zhelandovskiy.t1_aop_1.dto.TaskDto;
import com.zhelandovskiy.t1_aop_1.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {
    TaskDto toDto(TaskEntity entity);

    TaskEntity createUpdateDtoToEntity(TaskCreateUpdateDto createUpdateDto);

    void updateEntityFromDto(TaskCreateUpdateDto dto, @MappingTarget TaskEntity entity);
}
