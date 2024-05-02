package com.example.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.example.model.Registration;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends ArangoRepository<Registration, String> {

    Optional<Registration> findByHospitalIdAndPatientId(String hospitalId, String patientId);
    List<Registration> findByHospitalId(String hospitalId);
    List<Registration> findByPatientId(String patientId);
}
