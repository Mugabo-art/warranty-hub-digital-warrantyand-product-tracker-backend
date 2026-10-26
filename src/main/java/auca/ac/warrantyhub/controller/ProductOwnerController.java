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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.warrantyhub.model.ProductOwner;
import auca.ac.warrantyhub.service.ProductOwnerService;

@RestController
@RequestMapping("/api/product-owners")
public class ProductOwnerController {

    private final ProductOwnerService productOwnerService;

    public ProductOwnerController(ProductOwnerService productOwnerService) {
        this.productOwnerService = productOwnerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveProductOwner(@RequestBody ProductOwner productOwner) {
        String message = productOwnerService.saveProductOwner(productOwner);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductOwner>> getAllProductOwners() {
        List<ProductOwner> productOwners = productOwnerService.getAllProductOwners();
        return new ResponseEntity<List<ProductOwner>>(productOwners, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductOwnerById(@PathVariable UUID id) {
        Optional<ProductOwner> productOwner = productOwnerService.getProductOwnerById(id);
        if (productOwner.isPresent()) {
            return new ResponseEntity<Object>(productOwner.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("The product owner with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProductOwner(@PathVariable UUID id, @RequestBody ProductOwner productOwner) {
        String message = productOwnerService.updateProductOwner(id, productOwner);
        if (message.equals("The product owner with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductOwner(@PathVariable UUID id) {
        String message = productOwnerService.deleteProductOwner(id);
        if (message.equals("The product owner with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<ProductOwner>> getProductOwnersByLastName(@RequestParam String lastName) {
        List<ProductOwner> productOwners = productOwnerService.getProductOwnersByLastName(lastName);
        return new ResponseEntity<List<ProductOwner>>(productOwners, HttpStatus.OK);
    }

    @GetMapping("/of-reviewer/{reviewerId}")
    public ResponseEntity<Object> getProductOwnersOfReviewer(@PathVariable UUID reviewerId) {
        List<ProductOwner> productOwners = productOwnerService.getProductOwnersOfReviewer(reviewerId);
        if (productOwners == null) {
            return new ResponseEntity<Object>("The reviewer with that id does not exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Object>(productOwners, HttpStatus.OK);
        }
    }


}
