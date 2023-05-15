package com.project.springmicroserviceproject.model.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="crudtable")
public class CrudModel implements Serializable{
    
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int id;

      private String itemName;

      private int itemQuantity;

      @CreationTimestamp 
      private LocalDateTime createdOn;

      @UpdateTimestamp
      private LocalDateTime lastUpdatedOn;



    
}
