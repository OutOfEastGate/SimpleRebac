package xyz.wanghongtao.rebac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.wanghongtao.rebac.object.dataObject.model.Definition;
import xyz.wanghongtao.rebac.object.dataObject.model.Permission;
import xyz.wanghongtao.rebac.object.dataObject.model.Relation;
import xyz.wanghongtao.rebac.object.form.AddModelForm;
import xyz.wanghongtao.rebac.object.form.PolicyForm;
import xyz.wanghongtao.rebac.service.StoreService;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/17 22:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelTest {

    @Autowired
    StoreService storeService;

    @Test
    public void testInsertModel() {
        storeService.addModel(buildMockModel());
    }

    public AddModelForm buildMockModel() {
        AddModelForm addModelForm = new AddModelForm();
        addModelForm.setName("第一个权限模型");
        addModelForm.setDescription("这是第一个权限模型描述");
        addModelForm.setStoreId(1L);
        addModelForm.setAppKey("1a6267d5-f298-4a49-9769-3c1b2bcb87c8");

        PolicyForm policyForm = new PolicyForm();
        policyForm.setDescription("这是策略描述");

        Permission permission = Permission.builder()
                .permission("canRead")
                .relationCanAccess("reader")
                .build();

        Relation relation = Relation.builder()
                .relation("reader")
                .subjectType("document")
                .build();

        Definition userDefinition = Definition.builder()
                .description("用户实体")
                .objectType("user")
                .build();

        Definition documentDefinition = Definition.builder()
                .description("文档实体")
                .objectType("document")
                .permissions(List.of(permission))
                .relations(List.of(relation))
                .build();
        policyForm.setDefinitions(List.of(userDefinition, documentDefinition));
        addModelForm.setPolicy(policyForm);

        return addModelForm;
    }
}
