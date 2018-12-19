package com.project.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.management.model.ParentTask;
@Repository
public interface ParentTaskRepository  extends JpaRepository<ParentTask,Integer>{

}
