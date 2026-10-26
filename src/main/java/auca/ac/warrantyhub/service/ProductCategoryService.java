package auca.ac.warrantyhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.ac.warrantyhub.model.ProductCategory;
import auca.ac.warrantyhub.repository.ProductCategoryRepository;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public String saveProductCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
        return "Product category saved successfully";
    }

    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        return productCategories;
    }

    public Optional<ProductCategory> getProductCategoryById(UUID id) {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);
        return productCategory;
    }

    public String updateProductCategory(UUID id, ProductCategory newData) {
        Optional<ProductCategory> existing = productCategoryRepository.findById(id);
        if (existing.isEmpty()) {
            return "The product category with that id does not exist";
        }
        ProductCategory productCategory = existing.get();
        productCategory.setName(newData.getName());
        productCategoryRepository.save(productCategory);
        return "Product category updated successfully";
    }

    public String deleteProductCategory(UUID id) {
        if (!productCategoryRepository.existsById(id)) {
            return "The product category with that id does not exist";
        }
        productCategoryRepository.deleteById(id);
        return "Product category deleted successfully";
    }

    public List<ProductCategory> getUnusedProductCategories() {
        List<ProductCategory> unusedProductCategories = productCategoryRepository.findUnusedProductCategories();
        return unusedProductCategories;
    }

}
