package com.example.service;

import com.example.exception.RegistrationConflictException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Hospital;

import java.util.List;

public interface HospitalService {

    List<Hospital> findAllByPatient(String patientId);
    Hospital findById(String hospitalId) throws ResourceNotFoundException;
    Hospital create(Hospital hospital);
    Hospital modify(Hospital hospital, String hospitalId) throws ResourceNotFoundException;
    void delete(String hospitalId) throws ResourceNotFoundException;

    void registerPatient(String hospitalId, String patientId) throws ResourceNotFoundException, RegistrationConflictException;
    void unregisterPatient(String hospitalId, String patientId) throws ResourceNotFoundException;
}
