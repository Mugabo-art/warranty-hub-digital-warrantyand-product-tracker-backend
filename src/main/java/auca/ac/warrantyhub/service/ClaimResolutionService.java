package auca.ac.warrantyhub.service;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.ac.warrantyhub.model.ClaimResolution;
import auca.ac.warrantyhub.model.ClaimStatus;
import auca.ac.warrantyhub.model.WarrantyClaim;
import auca.ac.warrantyhub.repository.ClaimResolutionRepository;
import auca.ac.warrantyhub.repository.WarrantyClaimRepository;

@Service
public class ClaimResolutionService {

    private final ClaimResolutionRepository claimResolutionRepository;
    private final WarrantyClaimRepository warrantyClaimRepository;

    public ClaimResolutionService(ClaimResolutionRepository claimResolutionRepository, WarrantyClaimRepository warrantyClaimRepository) {
        this.claimResolutionRepository = claimResolutionRepository;
        this.warrantyClaimRepository = warrantyClaimRepository;
    }

    public String saveClaimResolution(UUID warrantyClaimId, ClaimResolution claimResolution) {
        Optional<WarrantyClaim> existing = warrantyClaimRepository.findById(warrantyClaimId);
        if (existing.isEmpty()) {
            return "The warranty claim with that id does not exist";
        }
        WarrantyClaim warrantyClaim = existing.get();
        ClaimStatus status = warrantyClaim.getStatus();
        if (status != ClaimStatus.APPROVED) {
            return "Claim resolutions are only for APPROVED warranty claims";
        }
        LocalDate today = LocalDate.now();
        claimResolution.setResolvedDate(today);
        claimResolution.setWarrantyClaim(warrantyClaim);
        claimResolutionRepository.save(claimResolution);
        return "Claim resolution saved successfully";
    }

    public List<ClaimResolution> getResolutionsOfProductOwner(String email) {
        List<ClaimResolution> claimResolutions = claimResolutionRepository.findResolutionsOfProductOwner(email);
        return claimResolutions;
    }


}
