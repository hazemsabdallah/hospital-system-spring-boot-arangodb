package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.helper.BeanCopyUtils;
import com.example.model.Hospital;
import com.example.model.Patient;
import com.example.repository.HospitalRepository;
import com.example.repository.PatientRepository;
import com.example.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public PatientServiceImpl(
            PatientRepository patientRepository,
            HospitalRepository hospitalRepository,
            RegistrationRepository registrationRepository) {

        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public List<Patient> findAllByHospital(String hospitalId) throws ResourceNotFoundException {

        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() ->
                new ResourceNotFoundException("Hospital with id " + hospitalId + " is not found!"));
        return hospital.getPatients();
    }

    @Override
    public Patient findById(String patientId) throws ResourceNotFoundException {

        Optional<Patient> patient = patientRepository.findById(patientId);
        return patient.orElseThrow(() ->
                new ResourceNotFoundException("Patient with id " + patientId + " is not found!"));
    }

    @Override
    public Patient create(Patient patient) {

        return patientRepository.save(patient);
    }

    @Override
    public Patient modify(Patient patient, String patientId) throws ResourceNotFoundException {

        Patient existingPatient = this.findById(patientId);
        // helper class used to update only nonnull fields, this allows single property updates
        // another approach could be using ModelMapper and skipping null field
        BeanCopyUtils.copyNonNullProperties(patient, existingPatient);
        return patientRepository.save(existingPatient);
    }

    @Override
    public void delete(String patientId) throws ResourceNotFoundException {

        Patient existingPatient = this.findById(patientId);
        registrationRepository.findByPatientId(patientId).forEach(registrationRepository::delete);

        patientRepository.delete(existingPatient);
    }
}
