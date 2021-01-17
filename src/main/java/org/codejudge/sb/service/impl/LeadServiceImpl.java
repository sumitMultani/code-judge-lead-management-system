package org.codejudge.sb.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.codejudge.sb.entity.LeadEntity;
import org.codejudge.sb.exception.LeadException;
import org.codejudge.sb.mapper.LeadMapper;
import org.codejudge.sb.model.LeadCommunicationRequest;
import org.codejudge.sb.model.LeadRequest;
import org.codejudge.sb.model.LeadResponse;
import org.codejudge.sb.model.LeadStatus;
import org.codejudge.sb.repository.LeadRepository;
import org.codejudge.sb.service.LeadService;
import org.codejudge.sb.validator.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LeadServiceImpl implements LeadService {

	private final Logger logger = LoggerFactory.getLogger(LeadServiceImpl.class);

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private LeadMapper leadMapper;

	@Override
	public LeadResponse findById(String leadId) {
		validateLeadId(leadId);
		Optional<LeadEntity> lead = leadRepository.findById(leadId);
		if (lead.isPresent()) {
			return leadMapper.convertLeadEntityToLeadResponse(lead.get());
		} else {
			throw new LeadException("", new LeadRequest(), HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public LeadResponse generateLead(LeadRequest leadRequest) {
		validateCreateLeadRequest(leadRequest);
		LeadEntity leadEntity = leadMapper.convertLeadToLeadEntity(leadRequest);
		leadEntity.setId(getId());
		leadEntity.setStatus(LeadStatus.Created);
		leadEntity = leadRepository.save(leadEntity);
		return leadMapper.convertLeadEntityToLeadResponse(leadEntity);
	}

	private void validateCreateLeadRequest(LeadRequest leadRequest) {
		validateEmail(true, leadRequest.getEmail(), null);
		validateMobile(true, leadRequest.getMobile(), null);
		if (ValidatorUtils.isEmptyOrNull(leadRequest.getFirst_name())) {
			throw new LeadException("first_name is mandatory.", HttpStatus.BAD_REQUEST);
		} else if (ValidatorUtils.isEmptyOrNull(leadRequest.getLast_name())) {
			throw new LeadException("last_name is mandatory.", HttpStatus.BAD_REQUEST);
		} else if (ValidatorUtils.isEmptyOrNull(leadRequest.getLocation_string())) {
			throw new LeadException("location_string is mandatory.", HttpStatus.BAD_REQUEST);
		} else if (ValidatorUtils.isEmptyOrNull(leadRequest.getLocation_type())) {
			throw new LeadException("location_type is mandatory.", HttpStatus.BAD_REQUEST);
		}
	}

	private void validateMobile(boolean isCreateRequest, Long mobile, Long oldMobile) {
		logger.info("[validateMobile] Started. ");
		if (isCreateRequest) {
			if (ValidatorUtils.isEmptyOrNull(mobile)) {
				throw new LeadException("mobile number is mandatory.", HttpStatus.BAD_REQUEST);
			}
			// get lead by mobile;
			LeadEntity leadEntity = leadRepository.findByMobile(mobile);
			if (leadEntity != null) {
				throw new LeadException("Mobile Number Already Exist.", HttpStatus.BAD_REQUEST);
			}
		} else {
			if (!ValidatorUtils.isEmptyOrNull(mobile) && !mobile.equals(oldMobile)) {
				// get lead by mobile;
				LeadEntity leadEntity = leadRepository.findByMobile(mobile);
				if (leadEntity != null) {
					throw new LeadException("Mobile Number Already Exist.", HttpStatus.BAD_REQUEST);
				}
			}
		}
		logger.info("[validateMobile] Successfully.. ");
	}

	private void validateEmail(boolean isCreated, String email, String oldEmail) {
		logger.info("[validateEmail] Started. ");
		if (isCreated) {
			if (ValidatorUtils.isEmptyOrNull(email)) {
				throw new LeadException("EmailId should not be null or empty.", HttpStatus.BAD_REQUEST);
			}
			// get lead by emailId;
			LeadEntity leadEntity = leadRepository.findByEmail(email);
			if (leadEntity != null) {
				throw new LeadException("EmailId Already Exist.", HttpStatus.BAD_REQUEST);
			}
		} else {
			if (!ValidatorUtils.isEmptyOrNull(email) && !email.equalsIgnoreCase(oldEmail)) {
				// get lead by emailId;
				LeadEntity leadEntity = leadRepository.findByEmail(email);
				if (leadEntity != null) {
					throw new LeadException("EmailId Already Exist.", HttpStatus.BAD_REQUEST);
				}
			}
		}
		logger.info("[validateEmail] Successfully.. ");
	}

	private String getId() {
		UUID uuid = UUID.randomUUID();
		return String.valueOf(uuid);
	}

	@Override
	public LeadResponse updateLead(String leadId, LeadRequest leadRequest) {
		logger.info("[LeadServiceImpl] [updateLead] leadRequest : " + leadRequest + " ,leadId : " + leadId);
		if (leadRequest != null) {
			Optional<LeadEntity> leadEntityOpt = leadRepository.findById(leadId);
			if (leadEntityOpt.isPresent()) {
				LeadEntity leadEntity = leadEntityOpt.get();
				logger.info("[LeadServiceImpl] [updateLead] GET leadEntity : " + leadEntity);
				validateEmail(false, leadRequest.getEmail(), leadEntity.getEmail());
				validateMobile(false, leadRequest.getMobile(), leadEntity.getMobile());
				leadEntity.setEmail(leadRequest.getEmail());
				if (!ValidatorUtils.isEmptyOrNull(leadRequest.getFirst_name())) {
					leadEntity.setFirst_name(leadRequest.getFirst_name());
				}
				if (!ValidatorUtils.isEmptyOrNull(leadRequest.getLast_name())) {
					leadEntity.setLast_name(leadRequest.getLast_name());
				}
				if (!ValidatorUtils.isEmptyOrNull(leadRequest.getLocation_string())) {
					leadEntity.setLocation_string(leadRequest.getLocation_string());
				}
				if (!ValidatorUtils.isEmptyOrNull(leadRequest.getLocation_type())) {
					leadEntity.setLocation_type(leadRequest.getLocation_type());
				}
				if (!ValidatorUtils.isEmptyOrNull(leadRequest.getMobile())) {
					leadEntity.setMobile(leadRequest.getMobile());
				}
				logger.info("[LeadServiceImpl] [updateLead] UPDATED leadEntity : " + leadEntity);
				LeadEntity entity = leadRepository.save(leadEntity);
				logger.info("[LeadServiceImpl] [updateLead] After save to DB leadEntity : " + entity);
				LeadResponse response = new LeadResponse();
				response.setStatus(LeadStatus.success);
				return response;
			} else {
				throw new LeadException("Lead Id Not Found.", HttpStatus.NOT_FOUND);
			}
		}
		return null;
	}

	@Override
	public LeadResponse deleteLead(String leadId) {
		validateLeadId(leadId);
		Optional<LeadEntity> findById = leadRepository.findById(leadId);
		if (findById.isPresent()) {
			leadRepository.deleteById(leadId);
			LeadResponse response = new LeadResponse();
			response.setStatus(LeadStatus.success);
			return response;
		} else {
			throw new LeadException("Lead Id Not Found.", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public LeadResponse markLead(String leadId, LeadCommunicationRequest communicationRequest) {
		validateLeadId(leadId);
		Optional<LeadEntity> findById = leadRepository.findById(leadId);
		if (findById.isPresent()) {
			LeadEntity leadEntity = findById.get();
			if (leadEntity.getStatus().equals(LeadStatus.Created)) {
				String communication = communicationRequest.getCommunication();
				if (!ValidatorUtils.isEmptyOrNull(communication)) {
					leadEntity.setCommunication(communication);
					leadEntity.setStatus(LeadStatus.Contacted);
					leadRepository.save(leadEntity);
					LeadResponse response = new LeadResponse();
					response.setCommunication(communication);
					response.setStatus(LeadStatus.Contacted);
					return response;
				} else {
					throw new LeadException("communication is mandatory.", HttpStatus.BAD_REQUEST);
				}
			} else {
				throw new LeadException("Lead status must be CREATED in db.", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new LeadException("Lead Id Not Found.", HttpStatus.NOT_FOUND);
		}
	}

	private void validateLeadId(String leadId) {
		if (ValidatorUtils.isEmptyOrNull(leadId)) {
			throw new LeadException("Lead Id shhould not be null or empty.", HttpStatus.BAD_REQUEST);
		}
	}

}
