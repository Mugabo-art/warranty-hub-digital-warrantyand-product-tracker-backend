package auca.ac.warrantyhub.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import auca.ac.warrantyhub.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
