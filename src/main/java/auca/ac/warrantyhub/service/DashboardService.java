package auca.ac.warrantyhub.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import auca.ac.warrantyhub.model.ClaimStatus;
import auca.ac.warrantyhub.model.ServiceCenter;
import auca.ac.warrantyhub.repository.ClaimResolutionRepository;
import auca.ac.warrantyhub.repository.ProductOwnerRepository;
import auca.ac.warrantyhub.repository.ReviewerRepository;
import auca.ac.warrantyhub.repository.ServiceCenterRepository;
import auca.ac.warrantyhub.repository.WarrantyClaimRepository;

@Service
public class DashboardService {

    private final ProductOwnerRepository productOwnerRepository;
    private final ReviewerRepository reviewerRepository;
    private final ServiceCenterRepository serviceCenterRepository;
    private final WarrantyClaimRepository warrantyClaimRepository;
    private final ClaimResolutionRepository claimResolutionRepository;

    public DashboardService(ProductOwnerRepository productOwnerRepository,
                            ReviewerRepository reviewerRepository,
                            ServiceCenterRepository serviceCenterRepository,
                            WarrantyClaimRepository warrantyClaimRepository,
                            ClaimResolutionRepository claimResolutionRepository) {
        this.productOwnerRepository = productOwnerRepository;
        this.reviewerRepository = reviewerRepository;
        this.serviceCenterRepository = serviceCenterRepository;
        this.warrantyClaimRepository = warrantyClaimRepository;
        this.claimResolutionRepository = claimResolutionRepository;
    }

    public Map<String, Long> getDashboard() {
        long productOwners = productOwnerRepository.count();
        long reviewers = reviewerRepository.count();

        List<ServiceCenter> freeCenters = serviceCenterRepository.findFreeServiceCenters();
        long freeServiceCenters = freeCenters.size();

        LocalDate today = LocalDate.now();
        long claimsToday = warrantyClaimRepository.countByClaimDate(today);

        long submittedClaims = warrantyClaimRepository.countByStatus(ClaimStatus.SUBMITTED);
        long resolutions = claimResolutionRepository.count();

        Map<String, Long> dashboard = new LinkedHashMap<String, Long>();
        dashboard.put("productOwners", productOwners);
        dashboard.put("reviewers", reviewers);
        dashboard.put("freeServiceCenters", freeServiceCenters);
        dashboard.put("claimsToday", claimsToday);
        dashboard.put("submitted", submittedClaims);
        dashboard.put("resolutions", resolutions);
        return dashboard;
    }

}
