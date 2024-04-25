package xyz.wanghongtao.rebac.object.dataObject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@TableName("rebac_user")
public class UserDo {
  @TableId(type = IdType.AUTO)
  Long id;

  String username;

  String password;
}
