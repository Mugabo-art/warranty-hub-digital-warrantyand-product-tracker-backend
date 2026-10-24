package auca.ac.warrantyhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.ac.warrantyhub.model.Reviewer;
import auca.ac.warrantyhub.repository.ReviewerRepository;

@Service
public class ReviewerService {

    private final ReviewerRepository reviewerRepository;

    public ReviewerService(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    public String saveReviewer(Reviewer reviewer) {
        reviewerRepository.save(reviewer);
        return "Reviewer saved successfully";
    }

    public List<Reviewer> getAllReviewers() {
        List<Reviewer> reviewers = reviewerRepository.findAll();
        return reviewers;
    }

    public Optional<Reviewer> getReviewerById(UUID id) {
        Optional<Reviewer> reviewer = reviewerRepository.findById(id);
        return reviewer;
    }

    public String updateReviewer(UUID id, Reviewer newData) {
        Optional<Reviewer> existing = reviewerRepository.findById(id);
        if (existing.isEmpty()) {
            return "The reviewer with that id does not exist";
        }
        Reviewer reviewer = existing.get();
        reviewer.setFirstName(newData.getFirstName());
        reviewer.setLastName(newData.getLastName());
        reviewer.setEmail(newData.getEmail());
        reviewerRepository.save(reviewer);
        return "Reviewer updated successfully";
    }

    public String deleteReviewer(UUID id) {
        if (!reviewerRepository.existsById(id)) {
            return "The reviewer with that id does not exist";
        }
        reviewerRepository.deleteById(id);
        return "Reviewer deleted successfully";
    }
}
