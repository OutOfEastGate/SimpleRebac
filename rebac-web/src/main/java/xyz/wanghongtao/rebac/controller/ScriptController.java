package xyz.wanghongtao.rebac.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wanghongtao.rebac.engine.script.GroovyScriptExecuteEngine;
import xyz.wanghongtao.rebac.object.form.script.ExecuteScriptForm;
import xyz.wanghongtao.rebac.object.viewObject.Result;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
@RestController
@RequestMapping("/script")
public class ScriptController {
  @PostMapping("/execute")
  public Result<Object> scriptExecute(@RequestBody ExecuteScriptForm executeScriptForm) {
    return Result.success(GroovyScriptExecuteEngine.execute(executeScriptForm.getGroovyScript()));
  }
}
