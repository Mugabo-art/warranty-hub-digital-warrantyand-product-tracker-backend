package auca.ac.warrantyhub.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.ac.warrantyhub.model.WarrantyClaim;
import auca.ac.warrantyhub.model.ClaimStatus;
import auca.ac.warrantyhub.model.Reviewer;
import auca.ac.warrantyhub.repository.WarrantyClaimRepository;

@Service
public class WarrantyClaimService {

    private final WarrantyClaimRepository warrantyClaimRepository;

    public WarrantyClaimService(WarrantyClaimRepository warrantyClaimRepository) {
        this.warrantyClaimRepository = warrantyClaimRepository;
    }

    public String saveWarrantyClaim(WarrantyClaim warrantyClaim) {
        LocalDate today = LocalDate.now();
        LocalDate claimDate = warrantyClaim.getClaimDate();
        if (claimDate.isBefore(today)) {
            return "Warranty claim date cannot be in the past";
        }
        Reviewer reviewer = warrantyClaim.getReviewer();
        boolean reviewerIsBusy = warrantyClaimRepository.existsByReviewerAndClaimDateAndStatusNot(reviewer, claimDate, ClaimStatus.REJECTED);
        if (reviewerIsBusy) {
            return "Reviewer already has a claim on that date";
        }
        warrantyClaim.setStatus(ClaimStatus.SUBMITTED);
        warrantyClaimRepository.save(warrantyClaim);
        return "Warranty claim saved successfully";
    }


    public List<WarrantyClaim> getAllWarrantyClaims() {
        List<WarrantyClaim> warrantyClaims = warrantyClaimRepository.findAll();
        return warrantyClaims;
    }

    public Optional<WarrantyClaim> getWarrantyClaimById(UUID id) {
        Optional<WarrantyClaim> warrantyClaim = warrantyClaimRepository.findById(id);
        return warrantyClaim;
    }

    public String updateWarrantyClaim(UUID id, WarrantyClaim newData) {
        Optional<WarrantyClaim> existing = warrantyClaimRepository.findById(id);
        if (existing.isEmpty()) {
            return "The warranty claim with that id does not exist";
        }
        WarrantyClaim warrantyClaim = existing.get();
        warrantyClaim.setClaimDate(newData.getClaimDate());
        warrantyClaim.setStatus(newData.getStatus());
        warrantyClaim.setProductName(newData.getProductName());
        warrantyClaim.setFaultDescription(newData.getFaultDescription());
        warrantyClaim.setProductOwner(newData.getProductOwner());
        warrantyClaim.setReviewer(newData.getReviewer());
        warrantyClaimRepository.save(warrantyClaim);
        return "Warranty claim updated successfully";
    }

    public String deleteWarrantyClaim(UUID id) {
        if (!warrantyClaimRepository.existsById(id)) {
            return "The warranty claim with that id does not exist";
        }
        warrantyClaimRepository.deleteById(id);
        return "Warranty claim deleted successfully";
    }

    public List<WarrantyClaim> getWarrantyClaimsByStatus(ClaimStatus status) {
        List<WarrantyClaim> warrantyClaims = warrantyClaimRepository.findByStatusOrderByClaimDateAsc(status);
        return warrantyClaims;
    }

    public List<WarrantyClaim> getWarrantyClaimsBetween(LocalDate start, LocalDate end) {
        List<WarrantyClaim> warrantyClaims = warrantyClaimRepository.findByClaimDateBetweenOrderByClaimDateAsc(start, end);
        return warrantyClaims;
    }

}
