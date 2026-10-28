package auca.ac.warrantyhub.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import auca.ac.warrantyhub.model.ProductCategory;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {

    @Query("SELECT c FROM ProductCategory c WHERE c.reviewers IS EMPTY")
    List<ProductCategory> findUnusedProductCategories();

}
