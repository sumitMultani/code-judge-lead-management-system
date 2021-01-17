package org.codejudge.sb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LeadResponse {

	private String id;

	private String first_name;

	private String last_name;

	private Long mobile;

	private String email;

	private String location_type;

	private String location_string;

	private LeadStatus status;

	private String communication;

	private String reason;

}
