package auca.ac.warrantyhub.repository;
import auca.ac.warrantyhub.model.ServiceCenter;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import auca.ac.warrantyhub.model.Reviewer;

public interface ReviewerRepository extends JpaRepository<Reviewer, UUID> {

    @Query("SELECT r FROM Reviewer r "
            + "JOIN r.productCategories c "
            + "WHERE LOWER(c.name) = LOWER(:name)")
    List<Reviewer> findByProductCategoryName(@Param("name") String name);
    boolean existsByServiceCenter(ServiceCenter serviceCenter);

}
