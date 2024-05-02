package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Hospital;
import com.example.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    @Autowired
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping(value = "/patients/{patientId}/hospitals")
    public ResponseEntity<List<Hospital>> listHospitalsOfPatient(
            @PathVariable(name = "patientId") String patientId) {

        List<Hospital> hospitals = hospitalService.findAllByPatient(patientId);
        HttpStatus httpStatus = hospitals.isEmpty()? HttpStatus.NO_CONTENT: HttpStatus.OK;

        return new ResponseEntity<>(hospitals, httpStatus);
    }

    @PostMapping(value = "/hospitals")
    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital hospital) {

        return new ResponseEntity<>(hospitalService.create(hospital), HttpStatus.CREATED);
    }

    @PutMapping(value = "/hospitals/{hospitalId}")
    public ResponseEntity<Hospital> updateHospital(
            @RequestBody Hospital hospital,
            @PathVariable String hospitalId) throws ResourceNotFoundException {

        return new ResponseEntity<>(hospitalService.modify(hospital, hospitalId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/hospitals/{hospitalId}")
    public ResponseEntity<String> deleteHospital(@PathVariable String hospitalId) throws ResourceNotFoundException {

        hospitalService.delete(hospitalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
