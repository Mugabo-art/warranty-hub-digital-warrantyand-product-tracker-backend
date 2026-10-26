package auca.ac.warrantyhub.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import auca.ac.warrantyhub.model.ServiceCenter;
import auca.ac.warrantyhub.repository.ServiceCenterRepository;

@Service
public class ServiceCenterService {

    private final ServiceCenterRepository serviceCenterRepository;

    public ServiceCenterService(ServiceCenterRepository serviceCenterRepository) {
        this.serviceCenterRepository = serviceCenterRepository;
    }

    public String saveServiceCenter(ServiceCenter serviceCenter) {
        serviceCenterRepository.save(serviceCenter);
        return "Service center saved successfully";
    }

    public List<ServiceCenter> getAllServiceCenters() {
        return serviceCenterRepository.findAll();
    }

    public Optional<ServiceCenter> getServiceCenterById(UUID id) {
        return serviceCenterRepository.findById(id);
    }

    public String updateServiceCenter(UUID id, ServiceCenter newData) {
        Optional<ServiceCenter> existing = serviceCenterRepository.findById(id);
        if (existing.isEmpty()) {
            return "The service center with that id does not exist";
        }
        ServiceCenter serviceCenter = existing.get();
        serviceCenter.setCenterNumber(newData.getCenterNumber());
        serviceCenter.setName(newData.getName());
        serviceCenterRepository.save(serviceCenter);
        return "Service center updated successfully";
    }

    public String deleteServiceCenter(UUID id) {
        if (!serviceCenterRepository.existsById(id)) {
            return "The service center with that id does not exist";
        }
        serviceCenterRepository.deleteById(id);
        return "Service center deleted successfully";
    }

    public List<ServiceCenter> getFreeServiceCenters() {
        List<ServiceCenter> freeServiceCenters = serviceCenterRepository.findFreeServiceCenters();
        return freeServiceCenters;
    }

}
