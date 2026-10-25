package auca.ac.warrantyhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.ac.warrantyhub.model.WarrantyClaim;
import auca.ac.warrantyhub.model.ClaimStatus;

import auca.ac.warrantyhub.repository.WarrantyClaimRepository;

@Service
public class WarrantyClaimService {

    private final WarrantyClaimRepository warrantyClaimRepository;

    public WarrantyClaimService(WarrantyClaimRepository warrantyClaimRepository) {
        this.warrantyClaimRepository = warrantyClaimRepository;
    }

    public String saveWarrantyClaim(WarrantyClaim warrantyClaim) {
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

}
