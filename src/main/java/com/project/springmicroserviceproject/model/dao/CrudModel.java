package com.project.springmicroserviceproject.model.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;


@Data
@Entity
public class CrudModel implements Serializable{
    
      @Id
      @GeneratedValue
      private int id;

      private String itemName;

      private int itemQuantity;
      
      private Date entryTime;
    
}
