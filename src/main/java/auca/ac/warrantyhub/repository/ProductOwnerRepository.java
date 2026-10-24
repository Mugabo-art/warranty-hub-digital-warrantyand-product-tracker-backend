package auca.ac.warrantyhub.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.ac.warrantyhub.model.ProductOwner;

public interface ProductOwnerRepository extends JpaRepository<ProductOwner, UUID> {
}
