package com.project.springmicroserviceproject.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.springmicroserviceproject.exception.EntryMissingException;
import com.project.springmicroserviceproject.model.dao.CrudModel;
import com.project.springmicroserviceproject.repository.SpringProjectRepository;

@RestController
public class CrudController {

    @Autowired
    private SpringProjectRepository springProjectRepository;

    @GetMapping("/project/home-page")
    public String getHome(){
      
       return "hello-world!";
    }


    @PostMapping("/project")
    public ResponseEntity<Object> addNewItem(@RequestBody CrudModel data){
        CrudModel addedData = springProjectRepository.save(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                     .buildAndExpand(addedData.getId()).toUri();
        return ResponseEntity.created(location).build();                             
        
    }

    @PutMapping("/project/add/{id}")  //note @RequestBody is possible for PUT
    public ResponseEntity<Object> updateItemQuantity(@PathVariable int id, @RequestParam int quantityToBeAdded){

        Optional<CrudModel> retrievedData = springProjectRepository.findById(id);
        if(retrievedData.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        CrudModel data = retrievedData.get();
        data.setItemQuantity(data.getItemQuantity()+ quantityToBeAdded);
        springProjectRepository.save(data);
        return ResponseEntity.ok().build();

    }
   
    @GetMapping("/project/retrieve/{id}")
    public CrudModel getItemDetail(@PathVariable int id){

       Optional<CrudModel> retrievedData = springProjectRepository.findById(id);
       if(retrievedData.isEmpty()){
        throw new EntryMissingException(" No data available for id : "+id);
       } 
       return retrievedData.get();
    }

    @DeleteMapping("/project/delete/{id}")
    public String deleteItem(@PathVariable int id){
        springProjectRepository.deleteById(id);
        return "Successfully deleted";
        
    }
}
