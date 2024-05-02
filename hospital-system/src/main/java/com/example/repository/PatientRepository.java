package com.example.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.example.model.Patient;

public interface PatientRepository extends ArangoRepository<Patient, String> {
}
