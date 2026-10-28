package auca.ac.warrantyhub.controller;
import java.util.UUID;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import auca.ac.warrantyhub.model.ClaimResolution;
import auca.ac.warrantyhub.service.ClaimResolutionService;

@RestController
@RequestMapping("/api/claim-resolutions")
public class ClaimResolutionController {

    private final ClaimResolutionService claimResolutionService;

    public ClaimResolutionController(ClaimResolutionService claimResolutionService) {
        this.claimResolutionService = claimResolutionService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveClaimResolution(@RequestParam UUID warrantyClaimId, @RequestBody ClaimResolution claimResolution) {
        String message = claimResolutionService.saveClaimResolution(warrantyClaimId, claimResolution);
        if (message.equals("The warranty claim with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("Claim resolutions are only for APPROVED warranty claims")) {
            return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.CREATED);
        }
    }

    @GetMapping("/product-owner/{email}")
    public ResponseEntity<List<ClaimResolution>> getResolutionsOfProductOwner(@PathVariable String email) {
        List<ClaimResolution> claimResolutions = claimResolutionService.getResolutionsOfProductOwner(email);
        return new ResponseEntity<List<ClaimResolution>>(claimResolutions, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClaimResolution(@PathVariable UUID id) {
        String message = claimResolutionService.deleteClaimResolution(id);
        if (message.equals("The claim resolution with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

}
