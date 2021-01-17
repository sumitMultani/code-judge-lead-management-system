package org.codejudge.sb.error.messaging;

import org.codejudge.sb.model.LeadStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorMessage {

	private LeadStatus status;
	
	private String reason;

}
