package com.project.springmicroserviceproject.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import com.project.springmicroserviceproject.exception.InvalidFieldException;
import com.project.springmicroserviceproject.model.dao.CrudModel;
import com.project.springmicroserviceproject.repository.SpringProjectRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CrudController {

    @Autowired
    private SpringProjectRepository springProjectRepository;

    @GetMapping("/project/home-page")
    public String getHome(){
       log.info("============================== home-page-invoked======================");
       return "hello-world!";
    }


    @PostMapping("/project")
    public ResponseEntity<Object> addNewItem(@Valid @RequestBody CrudModel data, BindingResult result){
        
        if(result.hasErrors()){
            log.info("error:"+result.getFieldError().getDefaultMessage()+"for the field "+result.getFieldError().getField()) ;
            throw new InvalidFieldException(result,result.getFieldError().getDefaultMessage());
        }
        CrudModel addedData = springProjectRepository.save(data);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                     .buildAndExpand(addedData.getId()).toUri();
        return ResponseEntity.created(location).headers(createHeaders()).body(addedData);                             
        
    }

    @PutMapping("/project/add/{id}")  //note @RequestBody is possible for PUT
    public ResponseEntity<Object> updateItemQuantity(@PathVariable int id, @RequestParam int quantityToBeAdded){

        Optional<CrudModel> retrievedData = springProjectRepository.findById(id);
        if(retrievedData.isEmpty()){
            throw new EntryMissingException("No record found");
            //return ResponseEntity.notFound().build();
        }
        CrudModel data = retrievedData.get();
        data.setItemQuantity(data.getItemQuantity()+ quantityToBeAdded);
        springProjectRepository.save(data);
        return ResponseEntity.ok().headers(createHeaders()).body(retrievedData.get());

    }
   
    @GetMapping("/project/retrieve/{id}")
    public ResponseEntity<Object> getItemDetail(@PathVariable int id){

       Optional<CrudModel> retrievedData = springProjectRepository.findById(id);
       if(retrievedData.isEmpty()){
       // throw new EntryMissingException(" No data available for id : "+id);
     //  return ResponseEntity.noContent()
       //                     .header("Content-Type","application/json").build() ;
       throw new EntryMissingException("No record found");
                           
       } 
                     
       return new  ResponseEntity<Object>(retrievedData.get(),createHeaders(),HttpStatus.OK);
    }

    @DeleteMapping("/project/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id){
        try {
            springProjectRepository.deleteById(id);

            
            return new ResponseEntity<String>("Entry deleted successfully!", createHeaders(), HttpStatus.OK);

        } catch (Exception e) {
           // return ResponseEntity.noContent()
                         //   .header("Content-Type","application/json").build() ;
             throw new EntryMissingException("No record found");
        }
   
    }

    
    public HttpHeaders createHeaders(){

        HttpHeaders respHeader = new HttpHeaders();
        respHeader.set("Content-Type", "application/json");   
        return respHeader;
        
    }
}
