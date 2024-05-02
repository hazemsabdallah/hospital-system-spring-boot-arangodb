package com.example.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.example.model.Hospital;

import java.util.List;

public interface HospitalRepository extends ArangoRepository<Hospital, String> {

    List<Hospital> findByPatientsId(String patientId);
}
