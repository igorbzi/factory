package com.projedata.factory.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projedata.factory.dto.MeasurementUnitDTO;
import com.projedata.factory.enumerators.MeasurementUnit;

@Controller
@RequestMapping("measurement-units")
public class MeasurementUnitsController {

	@GetMapping
	public ResponseEntity<List<MeasurementUnitDTO>> findAll() {
	    return ResponseEntity.ok(
	        Arrays.stream(MeasurementUnit.values())
	              .map(MeasurementUnitDTO::new)
	              .toList()
	    );
	}
}