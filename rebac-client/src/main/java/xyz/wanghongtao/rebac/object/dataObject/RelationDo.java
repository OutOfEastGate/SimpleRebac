package xyz.wanghongtao.rebac.object.dataObject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;

/**
 * @author wanghongtao
 * @data 2023/7/16 16:10
 */
@Builder
@TableName("rebac_relation")
public class RelationDo {
    @TableId(type = IdType.AUTO)
    Long id;

    Long modelId;

    String objectType;

    String object;

    String relation;

    String subjectType;

    String subject;

    String triple;
}
