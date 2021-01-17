package org.codejudge.sb.repository;

import org.codejudge.sb.entity.LeadEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LeadRepository extends MongoRepository<LeadEntity, String> {

	public LeadEntity findByEmail(String email);

	public LeadEntity findByMobile(Long mobile);
}
