package xyz.wanghongtao.rebac.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;

@Mapper
public interface ModelMapper extends BaseMapper<ModelDo> {
}
