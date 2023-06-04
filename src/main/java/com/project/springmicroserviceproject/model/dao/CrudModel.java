package com.project.springmicroserviceproject.model.dao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

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

      @NotNull
      @Size(min=2, message="Item name must have at least 2 characters")
      private String itemName;

      @PositiveOrZero(message = "Item quantity has to be 0 or more")
      private int itemQuantity;

      @CreationTimestamp 
      private LocalDateTime createdOn;

      @UpdateTimestamp
      private LocalDateTime lastUpdatedOn;



    
}
