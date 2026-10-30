package auca.ac.warrantyhub.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;


import auca.ac.warrantyhub.model.ClaimStatus;
import auca.ac.warrantyhub.model.Reviewer;
import auca.ac.warrantyhub.model.WarrantyClaim;


public interface WarrantyClaimRepository extends JpaRepository<WarrantyClaim, UUID> {

    List<WarrantyClaim> findByStatusOrderByClaimDateAsc(ClaimStatus status);
    List<WarrantyClaim> findByClaimDateBetweenOrderByClaimDateAsc(LocalDate start, LocalDate end);
    boolean existsByReviewerAndClaimDateAndStatusNot(Reviewer reviewer, LocalDate claimDate, ClaimStatus status);
    long countByClaimDate(LocalDate claimDate);
    long countByStatus(ClaimStatus status);

    @Query("SELECT w.status, COUNT(w) FROM WarrantyClaim w "
            + "GROUP BY w.status")
    List<Object[]> countClaimsByStatus();
    
    @Modifying
    @Query("UPDATE WarrantyClaim w SET w.status = :rejected "
            + "WHERE w.reviewer.id = :reviewerId "
            + "AND w.claimDate = :date "
            + "AND w.status <> :approved")
    int cancelDay(@Param("reviewerId") UUID reviewerId,
                  @Param("date") LocalDate date,
                  @Param("rejected") ClaimStatus rejected,
                  @Param("approved") ClaimStatus approved);

}
