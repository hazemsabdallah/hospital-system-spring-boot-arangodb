package com.example.service;

import com.example.exception.RegistrationConflictException;
import com.example.exception.ResourceNotFoundException;
import com.example.helper.BeanCopyUtils;
import com.example.model.Hospital;
import com.example.model.Patient;
import com.example.model.Registration;
import com.example.repository.HospitalRepository;
import com.example.repository.PatientRepository;
import com.example.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public HospitalServiceImpl(
            HospitalRepository hospitalRepository,
            PatientRepository patientRepository,
            RegistrationRepository registrationRepository) {
        this.hospitalRepository = hospitalRepository;
        this.patientRepository = patientRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public List<Hospital> findAllByPatient(String patientId) {

        // this approach leverages jpa method names, another approach would have been to implement bidirectional
        // relation by adding a collection<hospitals> to the patient for easier access
        // another possible way is to depend on the edge entity to get the relations IDs and iterate to get each
        return hospitalRepository.findByPatientsId(patientId);
    }

    @Override
    public Hospital findById(String hospitalId) throws ResourceNotFoundException {

        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        return hospital.orElseThrow(() ->
                new ResourceNotFoundException("Hospital with id " + hospitalId + " is not found!"));
    }

    @Override
    public Hospital create(Hospital hospital) {

        return hospitalRepository.save(hospital);
    }

    @Override
    public Hospital modify(Hospital hospital, String hospitalId) throws ResourceNotFoundException {

        Hospital existingHospital = this.findById(hospitalId);
        // helper class used to update only nonnull fields, this allows single property updates
        // another approach could be using ModelMapper and skipping null field
        BeanCopyUtils.copyNonNullProperties(hospital, existingHospital);
        return hospitalRepository.save(existingHospital);
    }

    @Override
    public void delete(String hospitalId) throws ResourceNotFoundException {

        Hospital existingHospital = this.findById(hospitalId);
        registrationRepository.findByHospitalId(hospitalId).forEach(registrationRepository::delete);

        hospitalRepository.delete(existingHospital);
    }

    @Override
    public void registerPatient(String hospitalId, String patientId)
            throws ResourceNotFoundException, RegistrationConflictException {

        Hospital hospital = this.findById(hospitalId);
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("Patient with id " + patientId + " is not found!"));

        Optional<Registration> exists = registrationRepository.findByHospitalIdAndPatientId(hospitalId, patientId);
        if (exists.isPresent()) throw new RegistrationConflictException("Patient already registered!");

        registrationRepository.save(new Registration(patient, hospital));
    }

    @Override
    public void unregisterPatient(String hospitalId, String patientId) throws ResourceNotFoundException {

        Registration registration = registrationRepository.findByHospitalIdAndPatientId(hospitalId, patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient " + patientId + " is not registered at Hospital " + hospitalId + "!"));
        registrationRepository.delete(registration);
    }
}
