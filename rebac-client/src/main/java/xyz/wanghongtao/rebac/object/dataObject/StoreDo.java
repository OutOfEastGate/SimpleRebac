package xyz.wanghongtao.rebac.object.dataObject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:05
 */
@Data
@TableName("rebac_store")
public class StoreDo {

    @TableId(type = IdType.AUTO)
    Long id;

    String name;

    String description;

    String appKey;
}