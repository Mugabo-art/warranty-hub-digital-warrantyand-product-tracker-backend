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

}
