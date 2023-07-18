package xyz.wanghongtao.rebac.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;

@Mapper
public interface RelationMapper extends BaseMapper<RelationDo> {
}
