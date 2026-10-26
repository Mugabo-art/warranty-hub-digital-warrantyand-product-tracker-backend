package auca.ac.warrantyhub.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import auca.ac.warrantyhub.model.ServiceCenter;

public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, UUID> {

    @Query("SELECT s FROM ServiceCenter s "
            + "WHERE NOT EXISTS (SELECT r FROM Reviewer r WHERE r.serviceCenter = s) "
            + "ORDER BY s.centerNumber ASC")
    List<ServiceCenter> findFreeServiceCenters();

}
