package com.project.springmicroserviceproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.springmicroserviceproject.controller.CrudController;

@SpringBootTest
public class CrudControllerSmokeTest {
    
    @Autowired
    private CrudController crudController;

    @Test
    public void contextLoads() throws Exception{
       assertNotNull(crudController);

    }

}
