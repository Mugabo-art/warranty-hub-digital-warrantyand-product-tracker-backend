package auca.ac.warrantyhub.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.ac.warrantyhub.model.ClaimStatus;
import auca.ac.warrantyhub.model.Reviewer;
import auca.ac.warrantyhub.model.WarrantyClaim;


public interface WarrantyClaimRepository extends JpaRepository<WarrantyClaim, UUID> {

    List<WarrantyClaim> findByStatusOrderByClaimDateAsc(ClaimStatus status);
    List<WarrantyClaim> findByClaimDateBetweenOrderByClaimDateAsc(LocalDate start, LocalDate end);
    boolean existsByReviewerAndClaimDateAndStatusNot(Reviewer reviewer, LocalDate claimDate, ClaimStatus status);

}
