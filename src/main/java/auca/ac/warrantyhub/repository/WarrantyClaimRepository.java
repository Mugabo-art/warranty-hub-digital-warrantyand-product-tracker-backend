package auca.ac.warrantyhub.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.ac.warrantyhub.model.WarrantyClaim;

public interface WarrantyClaimRepository extends JpaRepository<WarrantyClaim, UUID> {
}
