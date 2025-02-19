package com.zhelandovskiy.t1_aop_1.repository;

import com.zhelandovskiy.t1_aop_1.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}