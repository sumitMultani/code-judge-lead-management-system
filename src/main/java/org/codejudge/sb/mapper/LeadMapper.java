package org.codejudge.sb.mapper;

import org.codejudge.sb.entity.LeadEntity;
import org.codejudge.sb.model.LeadRequest;
import org.codejudge.sb.model.LeadResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeadMapper {

	public LeadResponse convertLeadEntityToLeadResponse(LeadEntity leadEntity);

	public LeadEntity convertLeadToLeadEntity(LeadRequest lead);

}
