package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Patient;
import com.example.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping(value = "/hospitals/{hospitalId}/patients")
    public ResponseEntity<List<Patient>> listPatientsOfHospital(@PathVariable String hospitalId) throws ResourceNotFoundException {

        List<Patient> patients = patientService.findAllByHospital(hospitalId);
        HttpStatus httpStatus = patients.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK;

        return new ResponseEntity<>(patients, httpStatus);
    }

    @PostMapping(value = "/patients")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {

        return new ResponseEntity<>(patientService.create(patient), HttpStatus.CREATED);
    }

    @PutMapping(value = "/patients/{patientId}")
    public ResponseEntity<Patient> updatePatient(
            @RequestBody Patient patient,
            @PathVariable String patientId) throws ResourceNotFoundException {

        return new ResponseEntity<>(patientService.modify(patient, patientId), HttpStatus.OK);
    }

    @DeleteMapping(value = "patients/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable String patientId) throws ResourceNotFoundException {

        patientService.delete(patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
