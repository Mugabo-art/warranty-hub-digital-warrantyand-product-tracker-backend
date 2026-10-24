package auca.ac.warrantyhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.ac.warrantyhub.model.ProductOwner;
import auca.ac.warrantyhub.repository.ProductOwnerRepository;

@Service
public class ProductOwnerService {

    private final ProductOwnerRepository productOwnerRepository;

    public ProductOwnerService(ProductOwnerRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;
    }

    public String saveProductOwner(ProductOwner productOwner) {
        productOwnerRepository.save(productOwner);
        return "Product owner saved successfully";
    }

    public List<ProductOwner> getAllProductOwners() {
        List<ProductOwner> productOwners = productOwnerRepository.findAll();
        return productOwners;
    }

    public Optional<ProductOwner> getProductOwnerById(UUID id) {
        Optional<ProductOwner> productOwner = productOwnerRepository.findById(id);
        return productOwner;
    }

    public String updateProductOwner(UUID id, ProductOwner newData) {
        Optional<ProductOwner> existing = productOwnerRepository.findById(id);
        if (existing.isEmpty()) {
            return "The product owner with that id does not exist";
        }
        ProductOwner productOwner = existing.get();
        productOwner.setFirstName(newData.getFirstName());
        productOwner.setLastName(newData.getLastName());
        productOwner.setEmail(newData.getEmail());
        productOwner.setPhone(newData.getPhone());
        productOwnerRepository.save(productOwner);
        return "Product owner updated successfully";
    }

    public String deleteProductOwner(UUID id) {
        if (!productOwnerRepository.existsById(id)) {
            return "The product owner with that id does not exist";
        }
        productOwnerRepository.deleteById(id);
        return "Product owner deleted successfully";
    }
}
