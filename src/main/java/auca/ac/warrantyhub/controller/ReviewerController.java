package auca.ac.warrantyhub.controller;
import org.springframework.web.bind.annotation.PatchMapping;

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

import auca.ac.warrantyhub.model.Reviewer;
import auca.ac.warrantyhub.service.ReviewerService;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {

    private final ReviewerService reviewerService;

    public ReviewerController(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveReviewer(@RequestBody Reviewer reviewer) {
        String message = reviewerService.saveReviewer(reviewer);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reviewer>> getAllReviewers() {
        List<Reviewer> reviewers = reviewerService.getAllReviewers();
        return new ResponseEntity<List<Reviewer>>(reviewers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getReviewerById(@PathVariable UUID id) {
        Optional<Reviewer> reviewer = reviewerService.getReviewerById(id);
        if (reviewer.isPresent()) {
            return new ResponseEntity<Object>(reviewer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("The reviewer with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReviewer(@PathVariable UUID id, @RequestBody Reviewer reviewer) {
        String message = reviewerService.updateReviewer(id, reviewer);
        if (message.equals("The reviewer with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReviewer(@PathVariable UUID id) {
        String message = reviewerService.deleteReviewer(id);
        if (message.equals("The reviewer with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/by-product-category")
    public ResponseEntity<List<Reviewer>> getReviewersByProductCategory(@RequestParam String name) {
        List<Reviewer> reviewers = reviewerService.getReviewersByProductCategory(name);
        return new ResponseEntity<List<Reviewer>>(reviewers, HttpStatus.OK);
    }

    @PutMapping("/{reviewerId}/product-categories/{productCategoryId}")
    public ResponseEntity<String> addProductCategoryToReviewer(@PathVariable UUID reviewerId, @PathVariable UUID productCategoryId) {
        String message = reviewerService.addProductCategoryToReviewer(reviewerId, productCategoryId);
        if (message.equals("The reviewer with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("That product category does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("The reviewer already has that product category")) {
            return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @PatchMapping("/{reviewerId}/service-center")
    public ResponseEntity<String> changeServiceCenter(@PathVariable UUID reviewerId, @RequestParam int centerNumber) {
        String message = reviewerService.changeServiceCenter(reviewerId, centerNumber);
        if (message.equals("The reviewer with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("The service center with that centerNumber does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("That service center is taken by another reviewer")) {
            return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

}
