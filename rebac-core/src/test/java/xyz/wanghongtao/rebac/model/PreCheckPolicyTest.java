package xyz.wanghongtao.rebac.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.wanghongtao.rebac.AbstractTest;
import xyz.wanghongtao.rebac.engine.model.ModelEngine;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.form.model.AddModelForm;
import xyz.wanghongtao.rebac.object.form.policy.PolicyForm;
import xyz.wanghongtao.rebac.util.JacksonUtils;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-21
 */
@Slf4j
public class PreCheckPolicyTest extends AbstractTest {

  @Test
  public void testPreCheckPolicy() {
    var policyStr = readFileToString("/model/policy.json");
    PolicyDo policyDo = JacksonUtils.fromJsonStr(policyStr, PolicyDo.class);
    AddModelForm addModelForm = new AddModelForm();
    assert policyDo != null;
    addModelForm.setName("mockName");
    addModelForm.setPolicy(PolicyForm.builder()
      .description(policyDo.getDescription())
      .definitions(policyDo.getDefinitions())
      .build());
    ModelEngine.preCheckPolicy(addModelForm);
  }
}
