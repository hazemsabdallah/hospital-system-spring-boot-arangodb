package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> findAllByHospital(String hospitalId) throws ResourceNotFoundException;
    Patient findById(String patientId) throws ResourceNotFoundException;
    Patient create(Patient patient);
    Patient modify(Patient patient, String patientId) throws ResourceNotFoundException;
    void delete(String patientId) throws ResourceNotFoundException;
}
