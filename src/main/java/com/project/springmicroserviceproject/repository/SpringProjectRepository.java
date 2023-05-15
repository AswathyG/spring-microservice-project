package com.project.springmicroserviceproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.springmicroserviceproject.model.dao.CrudModel;

@Repository
public interface SpringProjectRepository extends JpaRepository<CrudModel,Integer> {
    
}
