package xyz.wanghongtao.rebac.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.wanghongtao.rebac.object.dataObject.FileDo;

import java.util.List;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
@Repository
public interface FileRepository extends MongoRepository<FileDo, String> {
  List<FileDo> findByObjectId(String objectId);
}
