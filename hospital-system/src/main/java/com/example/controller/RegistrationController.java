package com.example.controller;

import com.example.exception.RegistrationConflictException;
import com.example.exception.ResourceNotFoundException;
import com.example.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/registrations/hospitals/{hospitalId}/patients/{patientId}")
public class RegistrationController {

    private final HospitalService hospitalService;

    @Autowired
    public RegistrationController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping
    public ResponseEntity<String> RegisterPatientInHospital(
            @PathVariable(name = "hospitalId") String hospitalId,
            @PathVariable(name = "patientId") String patientId)
            throws ResourceNotFoundException, RegistrationConflictException {

        hospitalService.registerPatient(hospitalId, patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> unregisterPatientFromHospital(
            @PathVariable(name = "hospitalId") String hospitalId,
            @PathVariable(name = "patientId") String patientId) throws ResourceNotFoundException {

        hospitalService.unregisterPatient(hospitalId, patientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
