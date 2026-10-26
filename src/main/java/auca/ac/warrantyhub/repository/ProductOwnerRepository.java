package auca.ac.warrantyhub.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.ac.warrantyhub.model.ProductOwner;

public interface ProductOwnerRepository extends JpaRepository<ProductOwner, UUID> {

    List<ProductOwner> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT w.productOwner FROM WarrantyClaim w "
            + "WHERE w.reviewer.id = :reviewerId")
    List<ProductOwner> findOwnersOfReviewer(@Param("reviewerId") UUID reviewerId);

}
