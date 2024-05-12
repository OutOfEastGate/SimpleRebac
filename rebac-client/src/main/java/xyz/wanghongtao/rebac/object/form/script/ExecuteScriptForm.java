package xyz.wanghongtao.rebac.object.form.script;

import lombok.Data;

import java.util.Map;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
@Data
public class ExecuteScriptForm {
  String groovyScript;

  Map<String, Object> params;
}
