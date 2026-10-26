package auca.ac.warrantyhub.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.ac.warrantyhub.model.ServiceCenter;
import auca.ac.warrantyhub.service.ServiceCenterService;

@RestController
@RequestMapping("/api/service-centers")
public class ServiceCenterController {

    private final ServiceCenterService serviceCenterService;

    public ServiceCenterController(ServiceCenterService serviceCenterService) {
        this.serviceCenterService = serviceCenterService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveServiceCenter(@RequestBody ServiceCenter serviceCenter) {
        String message = serviceCenterService.saveServiceCenter(serviceCenter);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceCenter>> getAllServiceCenters() {
        List<ServiceCenter> serviceCenters = serviceCenterService.getAllServiceCenters();
        return new ResponseEntity<List<ServiceCenter>>(serviceCenters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getServiceCenterById(@PathVariable UUID id) {
        Optional<ServiceCenter> serviceCenter = serviceCenterService.getServiceCenterById(id);
        if (serviceCenter.isPresent()) {
            return new ResponseEntity<Object>(serviceCenter.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>("The service center with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateServiceCenter(@PathVariable UUID id, @RequestBody ServiceCenter serviceCenter) {
        String message = serviceCenterService.updateServiceCenter(id, serviceCenter);
        if (message.equals("The service center with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteServiceCenter(@PathVariable UUID id) {
        String message = serviceCenterService.deleteServiceCenter(id);
        if (message.equals("The service center with that id does not exist")) {
            return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/free")
    public ResponseEntity<List<ServiceCenter>> getFreeServiceCenters() {
        List<ServiceCenter> freeServiceCenters = serviceCenterService.getFreeServiceCenters();
        return new ResponseEntity<List<ServiceCenter>>(freeServiceCenters, HttpStatus.OK);
    }

}
