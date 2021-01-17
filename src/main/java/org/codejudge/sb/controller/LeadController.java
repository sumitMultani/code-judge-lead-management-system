package org.codejudge.sb.controller;

import org.codejudge.sb.model.LeadCommunicationRequest;
import org.codejudge.sb.model.LeadRequest;
import org.codejudge.sb.model.LeadResponse;
import org.codejudge.sb.service.LeadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping
public class LeadController {

	private final Logger logger = LoggerFactory.getLogger(LeadController.class);

	@Autowired
	private LeadService leadService;

	@ApiOperation("fetch the lead by lead_id.")
	@GetMapping("/api/leads/{lead_id}")
	@ResponseStatus(code = HttpStatus.OK)
	public LeadResponse fetchLead(@PathVariable("lead_id") String leadId) {
		return leadService.findById(leadId);
	}

	@ApiOperation("generate a lead.")
	@PostMapping("/api/leads")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LeadResponse generateLead(@RequestBody LeadRequest leadRequest) {
		return leadService.generateLead(leadRequest);
	}

	@ApiOperation("update a lead.")
	@PutMapping("/api/leads/{lead_id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public LeadResponse updateLead(@PathVariable("lead_id") String leadId, @RequestBody LeadRequest leadRequest) {
		logger.info("[LeadController] [updateLead] Request Payload : " + leadRequest + " ,leadId : " + leadId);
		return leadService.updateLead(leadId, leadRequest);
	}

	@ApiOperation("delete a lead.")
	@DeleteMapping("/api/leads/{lead_id}")
	@ResponseStatus(code = HttpStatus.OK)
	public LeadResponse deleteLead(@PathVariable("lead_id") String leadId) {
		return leadService.deleteLead(leadId);
	}

	@ApiOperation("mark a lead.")
	@PutMapping("/api/mark_lead/{lead_id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public LeadResponse markLead(@PathVariable("lead_id") String leadId,
			@RequestBody LeadCommunicationRequest communicationRequest) {
		return leadService.markLead(leadId, communicationRequest);
	}
}
