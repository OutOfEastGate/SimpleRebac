package xyz.wanghongtao.rebac.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;

/**
 * @author wanghongtao
 * @data 2023/7/16 18:19
 */
@Mapper
public interface StoreMapper extends BaseMapper<StoreDo> {
}
