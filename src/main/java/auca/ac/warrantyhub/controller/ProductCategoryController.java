package auca.ac.warrantyhub.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.warrantyhub.model.ProductCategory;
import auca.ac.warrantyhub.service.ProductCategoryService;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveProductCategory(@RequestBody ProductCategory productCategory) {
        String message = productCategoryService.saveProductCategory(productCategory);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategories();
        return new ResponseEntity<List<ProductCategory>>(productCategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductCategoryById(@PathVariable UUID id) {
        Optional<ProductCategory> productCategory = productCategoryService.getProductCategoryById(id);
        if (productCategory.isPresent()) {
            return new ResponseEntity<Object>(productCategory.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("The product category with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProductCategory(@PathVariable UUID id,
            @RequestBody ProductCategory productCategory) {
        String message = productCategoryService.updateProductCategory(id, productCategory);
        if (message.equals("The product category with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductCategory(@PathVariable UUID id) {
        String message = productCategoryService.deleteProductCategory(id);
        if (message.equals("The product category with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }
}
