package org.codejudge.sb.service;

import org.codejudge.sb.model.LeadCommunicationRequest;
import org.codejudge.sb.model.LeadRequest;
import org.codejudge.sb.model.LeadResponse;

public interface LeadService {

	public LeadResponse findById(String leadId);

	public LeadResponse generateLead(LeadRequest leadRequest);

	public LeadResponse updateLead(String leadId, LeadRequest leadRequest);

	public LeadResponse deleteLead(String leadId);

	public LeadResponse markLead(String leadId, LeadCommunicationRequest communicationRequest);

}
