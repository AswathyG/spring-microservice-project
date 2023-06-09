package com.project.springmicroserviceproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.HeaderAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.project.springmicroserviceproject.controller.CrudController;
import com.project.springmicroserviceproject.exception.InvalidFieldException;
import com.project.springmicroserviceproject.model.dao.CrudModel;
import com.project.springmicroserviceproject.repository.SpringProjectRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.net.URI;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;;
// The example, in this case, is a Spring Boot application.
//  Fortunately the @SpringBootTest annotation already includes the @ExtendWith(SpringExtension.class) 
//  annotation, so we only need to include @SpringBootTest.
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(CrudController.class)
@ExtendWith(SpringExtension.class) // final !! it works woth these 3 annotations
@SpringBootTest
@AutoConfigureMockMvc
public class CrudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SpringProjectRepository springProjectRepository;

    @InjectMocks
    private CrudController crudController;

     private CrudModel mockCrudModel;

    @BeforeEach
    public void setup() {
        mockCrudModel = new CrudModel();
        mockCrudModel.setId(1);
        mockCrudModel.setItemName("Test Item");
        mockCrudModel.setItemQuantity(10);
    }

    @Test
    public void testgetHome() throws Exception {
        
        this.mockMvc.perform(get("/project/home-page"))
                                            .andDo(print())
                                            .andExpect(status().isOk())
                                            .andExpect(content().string(containsString("hello-world!")));
    }

     @Test
     public void testAddNewItem_ValidData_ReturnsCreatedResponse() {
         // Arrange
         CrudModel data = new CrudModel();
         data.setItemName("Test Item");
         data.setItemQuantity(10);
         BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
         Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
         Mockito.when(springProjectRepository.save(Mockito.any())).thenReturn(mockCrudModel);

         // Act
         ResponseEntity<Object> resp = crudController.addNewItem(data, mockBindingResult);

         // Assert
         assertEquals(HttpStatus.CREATED, resp.getStatusCode());
         assertEquals("/1", resp.getHeaders().getLocation().getPath()); 
         assertEquals(mockCrudModel, resp.getBody());

          // Verify the method is invoked with the correct parameter
        verify(springProjectRepository).save(any(CrudModel.class));

     }

     @Test
     public void testAddNewItem_InvalidData_ThrowsInvalidFieldException() {
         // Arrange
         CrudModel data = new CrudModel();
         data.setItemName(""); // Invalid name
         data.setItemQuantity(10);
         BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
         Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
         FieldError fieldError = new FieldError("data", "itemName", mockBindingResult, false, null, null, "Invalid Name");
         Mockito.when(mockBindingResult.getFieldError()).thenReturn(fieldError);
 
         // Act & Assert
         assertThrows(InvalidFieldException.class, () -> crudController.addNewItem(data, mockBindingResult));
     }

     @Test
    public void testUpdateItemQuantity_ExistingItem() throws Exception {
        int id = 1;
        int quantityToBeAdded = 5;

        CrudModel existingData = new CrudModel();
        existingData.setId(id);
        existingData.setItemQuantity(10);

        CrudModel expectedData = new CrudModel();
        expectedData.setId(id);
        expectedData.setItemQuantity(15);

        Mockito.when(springProjectRepository.findById(id)).thenReturn(Optional.of(existingData));
        Mockito.when(springProjectRepository.save(any(CrudModel.class))).thenReturn(existingData);

        HttpHeaders respHeader = new HttpHeaders();
        respHeader.set("Content-Type", "application/json");   

        
        ResponseEntity<Object> resp = crudController.updateItemQuantity(id, quantityToBeAdded);

        assertEquals(HttpStatus.OK,resp.getStatusCode());
        assertEquals(respHeader,resp.getHeaders());
        assertEquals(expectedData,resp.getBody());
        // this.mockMvc.perform(MockMvcRequestBuilders.put("/project/add/{id}", id)
        //         .param("quantityToBeAdded", String.valueOf(quantityToBeAdded)))
        //        // .andExpect(request().attribute("Content-Type","application/json"))
        //         .andExpect(status().isOk())
        //         .andExpect(header().string("Content-Type", "application/json"))
        //         .andExpect(jsonPath("$.id").value(id))
        //         .andExpect(jsonPath("$.itemQuantity").value(15));

        // Verify the method is invoked with the correct parameters
        verify(springProjectRepository).findById(id);
        verify(springProjectRepository).save(any(CrudModel.class));
    }

//     @Test
//     public void testUpdateItemQuantity_NonExistingItem() throws Exception {
//         int id = 1;
//         int quantityToBeAdded = 5;

//         when(springProjectRepository.findById(id)).thenReturn(Optional.empty());

//         mockMvc.perform(MockMvcRequestBuilders.put("/project/add/{id}", id)
//                 .param("quantityToBeAdded", String.valueOf(quantityToBeAdded)))
//                 .andExpect(MockMvcResultMatchers.status().isNotFound());

//         // Verify the method is invoked with the correct parameter
//         verify(springProjectRepository).findById(id);
//         verifyNoMoreInteractions(springProjectRepository);
//     }

//     @Test
//     public void testGetItemDetail_ExistingItem() throws Exception {
//         int id = 1;

//         CrudModel existingData = new CrudModel();
//         existingData.setId(id);
//         existingData.setName("Test");
//         existingData.setDescription("Test description");

//         when(springProjectRepository.findById(id)).thenReturn(Optional.of(existingData));

//         mockMvc.perform(MockMvcRequestBuilders.get("/project/retrieve/{id}", id))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test"))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test description"));

//         // Verify the method is invoked with the correct parameter
//         verify(springProjectRepository).findById(id);
//     }

//     @Test
//     public void testGetItemDetail_NonExistingItem() throws Exception {
//         int id = 1;

//         when(springProjectRepository.findById(id)).thenReturn(Optional.empty());

//         mockMvc.perform(MockMvcRequestBuilders.get("/project/retrieve/{id}", id))
//                 .andExpect(MockMvcResultMatchers.status().isNotFound());

//         // Verify the method is invoked with the correct parameter
//         verify(springProjectRepository).findById(id);
//         verifyNoMoreInteractions(springProjectRepository);
//     }

//     @Test
//     public void testDeleteItem_ExistingItem() throws Exception {
//         int id = 1;

//         mockMvc.perform(MockMvcRequestBuilders.delete("/project/delete/{id}", id))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))
//                 .andExpect(MockMvcResultMatchers.content().string("Entry deleted successfully!"));

//         // Verify the method is invoked with the correct parameter
//         verify(springProjectRepository).deleteById(id);
//     }

//     @Test
//     public void testDeleteItem_NonExistingItem() throws Exception {
//         int id = 1;

//         doThrow(EntryMissingException.class).when(springProjectRepository).deleteById(id);

//         mockMvc.perform(MockMvcRequestBuilders.delete("/project/delete/{id}", id))
//                 .andExpect(MockMvcResultMatchers.status().isNotFound());

//         // Verify the method is invoked with the correct parameter
//         verify(springProjectRepository).deleteById(id);
//     }
// }
    
}
