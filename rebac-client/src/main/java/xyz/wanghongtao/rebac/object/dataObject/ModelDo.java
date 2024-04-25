package xyz.wanghongtao.rebac.object.dataObject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:11
 */
@NoArgsConstructor
@AllArgsConstructor
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
