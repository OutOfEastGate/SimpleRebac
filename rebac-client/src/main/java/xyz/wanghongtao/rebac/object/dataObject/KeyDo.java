package xyz.wanghongtao.rebac.object.dataObject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:26
 */
@Data
@TableName("rebac_key")
public class KeyDo {
    @TableId(type = IdType.AUTO)
    Long id;

    String appName;

    String appIcon;

    String appKey;

    String secretKey;
}
