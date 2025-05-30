package com.zhelandovskiy.t1_task_service.mapper;

import com.zhelandovskiy.t1_task_service.dto.TaskCreateUpdateDto;
import com.zhelandovskiy.t1_task_service.dto.TaskDto;
import com.zhelandovskiy.t1_task_service.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {
    TaskDto toDto(TaskEntity entity);

    List<TaskDto> toDtoList(List<TaskEntity> entityList);

    TaskEntity createUpdateDtoToEntity(TaskCreateUpdateDto createUpdateDto);

    void updateEntityFromDto(TaskCreateUpdateDto dto, @MappingTarget TaskEntity entity);
}
