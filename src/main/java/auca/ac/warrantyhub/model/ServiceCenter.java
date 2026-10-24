package auca.ac.warrantyhub.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_center")
public class ServiceCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int centerNumber;

    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCenterNumber() {
        return centerNumber;
    }

    public void setCenterNumber(int centerNumber) {
        this.centerNumber = centerNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
