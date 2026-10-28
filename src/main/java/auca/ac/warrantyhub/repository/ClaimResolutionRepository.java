package auca.ac.warrantyhub.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.ac.warrantyhub.model.ClaimResolution;

public interface ClaimResolutionRepository extends JpaRepository<ClaimResolution, UUID> {

    @Query("SELECT c FROM ClaimResolution c "
            + "WHERE c.warrantyClaim.productOwner.email = :email "
            + "ORDER BY c.resolvedDate DESC")
    List<ClaimResolution> findResolutionsOfProductOwner(@Param("email") String email);

}
