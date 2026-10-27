package auca.ac.warrantyhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.ac.warrantyhub.model.Reviewer;
import auca.ac.warrantyhub.repository.ReviewerRepository;
import auca.ac.warrantyhub.repository.ProductCategoryRepository;
import auca.ac.warrantyhub.model.ProductCategory;


@Service
public class ReviewerService {

    private final ReviewerRepository reviewerRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ReviewerService(ReviewerRepository reviewerRepository, ProductCategoryRepository productCategoryRepository) {
        this.reviewerRepository = reviewerRepository;
        this.productCategoryRepository = productCategoryRepository;
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

    public List<Reviewer> getReviewersByProductCategory(String name) {
        List<Reviewer> reviewers = reviewerRepository.findByProductCategoryName(name);
        return reviewers;
    }

    public String addProductCategoryToReviewer(UUID reviewerId, UUID productCategoryId) {
        Optional<Reviewer> existingReviewer = reviewerRepository.findById(reviewerId);
        if (existingReviewer.isEmpty()) {
            return "The reviewer with that id does not exist";
        }
        Optional<ProductCategory> existingProductCategory = productCategoryRepository.findById(productCategoryId);
        if (existingProductCategory.isEmpty()) {
            return "That product category does not exist";
        }
        Reviewer reviewer = existingReviewer.get();
        ProductCategory productCategory = existingProductCategory.get();
        List<ProductCategory> productCategories = reviewer.getProductCategories();
        if (productCategories.contains(productCategory)) {
            return "The reviewer already has that product category";
        }
        productCategories.add(productCategory);
        reviewerRepository.save(reviewer);
        return "Product category added to the reviewer";
    }

}
