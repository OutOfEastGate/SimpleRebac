package xyz.wanghongtao.rebac.engine.script;

import xyz.wanghongtao.rebac.service.SystemService;

import java.util.Map;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-10
 */
public class GroovyScriptBase {

  protected final String id = "1";

  public SystemService systemService;

  public Map<String, Object> $Params;

  protected Object fn() {
    return null;
  }
}
