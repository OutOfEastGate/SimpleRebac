package xyz.wanghongtao.rebac.object.dataObject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:11
 */
@Builder
@Data
@TableName("rebac_model")
public class ModelDo {
    @TableId(type = IdType.AUTO)
    Long id;

    Long storeId;

    String name;

    String description;

    String policyId;
}
