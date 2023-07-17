package xyz.wanghongtao.rebac.object.dataObject.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/17 22:35
 */
@Builder
@Data
public class Permission {
    /**
     * 定义权限名称，如 canWrite
     */
    String permission;

    /**
     * 定义哪种关系可以授权该权限
     */
    String relationCanAccess;
}
