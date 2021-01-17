package org.codejudge.sb.entity;

import org.codejudge.sb.model.LeadStatus;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "lead")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeadEntity {

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
