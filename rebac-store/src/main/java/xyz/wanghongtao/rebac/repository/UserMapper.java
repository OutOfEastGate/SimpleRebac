package xyz.wanghongtao.rebac.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.wanghongtao.rebac.object.dataObject.UserDo;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDo> {
}
