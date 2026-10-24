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

import auca.ac.warrantyhub.model.WarrantyClaim;
import auca.ac.warrantyhub.service.WarrantyClaimService;

@RestController
@RequestMapping("/api/warranty-claims")
public class WarrantyClaimController {

    private final WarrantyClaimService warrantyClaimService;

    public WarrantyClaimController(WarrantyClaimService warrantyClaimService) {
        this.warrantyClaimService = warrantyClaimService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveWarrantyClaim(@RequestBody WarrantyClaim warrantyClaim) {
        String message = warrantyClaimService.saveWarrantyClaim(warrantyClaim);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WarrantyClaim>> getAllWarrantyClaims() {
        List<WarrantyClaim> warrantyClaims = warrantyClaimService.getAllWarrantyClaims();
        return new ResponseEntity<List<WarrantyClaim>>(warrantyClaims, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getWarrantyClaimById(@PathVariable UUID id) {
        Optional<WarrantyClaim> warrantyClaim = warrantyClaimService.getWarrantyClaimById(id);
        if (warrantyClaim.isPresent()) {
            return new ResponseEntity<Object>(warrantyClaim.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("The warranty claim with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWarrantyClaim(@PathVariable UUID id, @RequestBody WarrantyClaim warrantyClaim) {
        String message = warrantyClaimService.updateWarrantyClaim(id, warrantyClaim);
        if (message.equals("The warranty claim with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWarrantyClaim(@PathVariable UUID id) {
        String message = warrantyClaimService.deleteWarrantyClaim(id);
        if (message.equals("The warranty claim with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }
}
