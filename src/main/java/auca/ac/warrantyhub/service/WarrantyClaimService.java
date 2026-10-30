package auca.ac.warrantyhub.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import auca.ac.warrantyhub.model.WarrantyClaim;
import auca.ac.warrantyhub.model.ClaimStatus;
import auca.ac.warrantyhub.model.Reviewer;
import auca.ac.warrantyhub.repository.WarrantyClaimRepository;
import auca.ac.warrantyhub.repository.ClaimResolutionRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WarrantyClaimService {

    private final WarrantyClaimRepository warrantyClaimRepository;
    private final ClaimResolutionRepository claimResolutionRepository;

    public WarrantyClaimService(WarrantyClaimRepository warrantyClaimRepository, ClaimResolutionRepository claimResolutionRepository) {
        this.warrantyClaimRepository = warrantyClaimRepository;
        this.claimResolutionRepository = claimResolutionRepository;
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
        warrantyClaim.setProductName(newData.getProductName());
        warrantyClaim.setFaultDescription(newData.getFaultDescription());
        warrantyClaim.setProductOwner(newData.getProductOwner());
        warrantyClaim.setReviewer(newData.getReviewer());
        warrantyClaimRepository.save(warrantyClaim);
        return "Warranty claim updated successfully";
    }

    public String updateWarrantyClaimStatus(UUID id, ClaimStatus newStatus) {
        Optional<WarrantyClaim> existing = warrantyClaimRepository.findById(id);
        if (existing.isEmpty()) {
            return "The warranty claim with that id does not exist";
        }
        WarrantyClaim warrantyClaim = existing.get();
        ClaimStatus currentStatus = warrantyClaim.getStatus();
        boolean allowed = false;
        if (currentStatus == ClaimStatus.SUBMITTED) {
            if (newStatus == ClaimStatus.UNDER_REVIEW || newStatus == ClaimStatus.REJECTED) {
                allowed = true;
            }
        } else if (currentStatus == ClaimStatus.UNDER_REVIEW) {
            if (newStatus == ClaimStatus.APPROVED || newStatus == ClaimStatus.REJECTED) {
                allowed = true;
            }
        }
        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }
        warrantyClaim.setStatus(newStatus);
        warrantyClaimRepository.save(warrantyClaim);
        return "Warranty claim status updated successfully";
    }

    public String deleteWarrantyClaim(UUID id) {
        Optional<WarrantyClaim> existing = warrantyClaimRepository.findById(id);
        if (existing.isEmpty()) {
            return "The warranty claim with that id does not exist";
        }
        WarrantyClaim warrantyClaim = existing.get();
        boolean hasResolutions = claimResolutionRepository.existsByWarrantyClaim(warrantyClaim);
        if (hasResolutions) {
            return "That warranty claim has claim resolutions, delete them first";
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

    public List<Object[]> getClaimCountsByStatus() {
        List<Object[]> counts = warrantyClaimRepository.countClaimsByStatus();
        return counts;
    }

    @Transactional
    public String cancelDay(UUID reviewerId, LocalDate date) {
        int cancelled = warrantyClaimRepository.cancelDay(reviewerId, date, ClaimStatus.REJECTED, ClaimStatus.APPROVED);
        return cancelled + " warranty claims cancelled";
    }
    
    public Page<WarrantyClaim> getWarrantyClaimsPage(Pageable pageable) {
        Page<WarrantyClaim> claimsPage = warrantyClaimRepository.findAll(pageable);
        return claimsPage;
    }


}
