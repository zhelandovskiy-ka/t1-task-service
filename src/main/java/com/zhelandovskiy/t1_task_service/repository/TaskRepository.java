package com.zhelandovskiy.t1_task_service.repository;

import com.zhelandovskiy.t1_task_service.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}