package xyz.wanghongtao.rebac.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphDo;

@Repository
public interface GraphRepository extends MongoRepository<GraphDo, String> {
}
