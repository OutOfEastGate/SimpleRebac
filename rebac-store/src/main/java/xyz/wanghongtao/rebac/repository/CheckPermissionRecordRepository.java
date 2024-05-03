package xyz.wanghongtao.rebac.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;

import java.util.List;

@Repository
public interface CheckPermissionRecordRepository extends MongoRepository<CheckPermissionRecordDo, String> {
  List<CheckPermissionRecordDo> findTop20ByOrderByCreateTimestampDesc();
}
