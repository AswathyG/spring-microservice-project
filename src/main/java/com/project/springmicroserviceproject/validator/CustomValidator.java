// package com.project.springmicroserviceproject.validator;

// import org.springframework.stereotype.Component;
// import org.springframework.validation.Errors;
// import org.springframework.validation.ValidationUtils;
// import org.springframework.validation.Validator;

// import com.project.springmicroserviceproject.model.dao.CrudModel;

// @Component
// public class CustomValidator implements Validator {
//     @Override
//     public boolean supports(Class<?> clazz) {
//         return CrudModel.class.equals(clazz);
//     }

//     @Override
//     public void validate(Object target, Errors errors) {
//         ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemQuantity", "field.required");
//         //The object whose field is being validated does not need to be passed in because the Errors instance can resolve field values by itself (it will usually hold an internal reference to the target object).
        
//         CrudModel crudModel = (CrudModel) target;
//         if (crudModel.getItemQuantity() < 0) {
//             errors.rejectValue("itemQuantity", "field.positiveOrZero");
//         }

//     }
// }
