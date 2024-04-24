package xyz.wanghongtao.rebac.object.dataObject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Data
@TableName("rebac_user")
public class UserDo {
  @TableId(type = IdType.AUTO)
  Long id;

  String username;

  String password;
}
