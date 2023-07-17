package xyz.wanghongtao.rebac.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;

@Repository
public interface PolicyRepository extends MongoRepository<PolicyDo, String> {
}
