
import React, {useEffect, useState} from 'react';
import {Button, Card, Form, FormProps, Input, Layout, message, notification, Select, Space, Tabs, Tooltip} from "antd";
import {Content, Footer, Header} from "antd/es/layout/layout";
import {checkPermission, checkRelation, getAllModel, getAllStore, getPolicy} from "../../request/api";
import Selector from "../graph/Selector";
import Relation from "../graph/Relation";
import {SmileOutlined} from "@ant-design/icons";


const headerStyle: React.CSSProperties = {
  textAlign: 'center',
  color: '#fff',
  height: 64,
  paddingInline: 50,
  lineHeight: '64px',
  backgroundColor: '#7dbcea',
};

const contentStyle: React.CSSProperties = {
  minHeight: 10,
  // lineHeight: '50px',
  color: '#fff',
  backgroundColor: '#f0f2f3',
};

const footerStyle: React.CSSProperties = {
  textAlign: 'center',
  color: '#fff',
  backgroundColor: '#7dbcea',
};

interface CheckPermissionParam{
  objectType:string,
  objectName:string,
  permission:string,
  subjectType:string,
  subjectName:string
}

interface CheckRelationParam{
  objectType:string,
  objectName:string,
  relation:string,
  subjectType:string,
  subjectName:string
}

function App() {
  const [api, contextHolder] = notification.useNotification();
  //权限model
  const [models, setModels] = useState<Model[]>([])
  //存储模型
  const [stores, setStores] = useState<Store[]>([]);
  //权限策略
  const [policy, setPolicy] = useState<Policy>();

  const [selectTab, setSelectTab] = useState<string>();

  const [currentSelectModel, setCurrentSelectModel] = useState<Model>()

  const [currentSelectObjectType, setCurrentSelectObjectType] = useState<string>()
  const [currentSelectSubjectType, setCurrentSelectSubjectType] = useState<string>()

  const onFinish: FormProps<CheckPermissionParam | CheckRelationParam>["onFinish"] = (values) => {
    if(selectTab === undefined) {
      api.open({
        message: '错误',
        description:"请选择存储模型",
        duration: 0,
        icon: <SmileOutlined style={{ color: '#108ee9' }} />
      });
      return
    }

    if("permission" in values && values.permission) {
      checkPermission({
        modelId:selectTab,
        triple: `${values.objectType}:${values.objectName}#${values.permission}@${values.subjectType}:${values.subjectName}`
      }).then(res => {
        if(res.msg === "success") {
          api.open({
            message: '权限校验结果',
            description:
              res.data.toString(),
            duration: 0,
          });
        } else {
          message.error(res.msg)
        }
      })
    } else if ("relation" in values && values.relation) {
      checkRelation({
        modelId:selectTab,
        triple: `${values.objectType}:${values.objectName}#${values.relation}@${values.subjectType}:${values.subjectName}`
      }).then(res => {
        if(res.msg === "success") {
          api.open({
            message: '关系校验结果',
            description:
              res.data.toString(),
            duration: 0,
          });
        } else {
          message.error(res.msg)
        }
      })
    } else {
      message.error("请选择权限或者关系")
    }
  };

  const typeSelectItems = policy === undefined ? [] : policy.definitions.map(def => {
    return {
      objectType: def.objectType,
      description: def.description
    }
  })
  const ops = typeSelectItems.map(item => ({
    value: item.objectType,
    label: <div>
      <Tooltip placement="topRight" title={item.description}>
        {item.objectType}
      </Tooltip></div>,
    desc: item.description,
  }))

  let permissions:PermissionDefinition[] = []
  let relations:RelationDefinition[] = []

  const permissionItems = currentSelectSubjectType === undefined || policy === undefined ? [] : policy.definitions.map(def => {
    if(def.objectType === currentSelectSubjectType) {
      permissions = def.permissions
      relations = def.relations
    }
  })

  const permissionOps = permissions.map(item => ({
    value: item.permission,
    label: item.permission,
    desc: item.permission,
  }))

  const relationOps = relations.map(item => ({
    value: item.relation,
    label: item.relation,
    desc: item.relation
  }))



  useEffect(() => {
    if (stores.length === 0) {
      getAllStore().then((res) => {
        if (res.msg === 'success' && res.data != null) {
          setStores(res.data);
        }
      }).catch(error => {
        message.error(error.message)
      });
    }
  }, []);
  const handleSelectChange = (id:number) => {
    getAllModel(id).then(res => {
      if (res.msg === "success") {
        setModels(res.data)
        if(res.data.length > 0) {
          setSelectTab(res.data[0].id)
          setCurrentSelectModel(res.data[0])
        }
      }
    })
  }

  const onTabChange = (key: string) => {
    setSelectTab(key)
    models.forEach(model => {
      if(model.id === key) {
        setCurrentSelectModel(model)
      }
    })
  };

  useEffect(() => {
    if(currentSelectModel !== undefined && currentSelectModel.policyId !== undefined) {
      getPolicy(currentSelectModel.policyId).then(r => {
        setPolicy(r.data)
      }).catch(err => {
        message.error("获取权限策略失败")
      })
    }
  }, [selectTab])

  function getDemo() {

    return <div style={{ display: 'flex', flexDirection: 'row' }}>
      <Card style={{ width: 200, flex: 1 }} title={"关系校验"}>
        <Form
          labelCol={{ span: 4 }}
          wrapperCol={{ span: 14 }}
          layout="horizontal"
          onFinish={onFinish}
          style={{ maxWidth: 600 }}
        >
          <Form.Item label="主体类型" name="objectType">
            <Select options={ops}></Select>
          </Form.Item>
          <Form.Item label="主体" name={"objectName"}>
            <Input />
          </Form.Item>
          <Form.Item label="客体类型" name={"subjectType"}>
            <Input />
          </Form.Item>
          <Form.Item label="客体" name={"subjectName"}>
            <Input />
          </Form.Item>
          <Form.Item label="关系" name={"relation"}>
            <Input />
          </Form.Item>
          <Form.Item label="操作">
            <Button type={"primary"} htmlType="submit"> 校验 </Button>
          </Form.Item>
        </Form>
      </Card>
      <Card style={{ width: 200, flex: 1 }} title={"权限校验"}>
        <Form
          labelCol={{ span: 4 }}
          wrapperCol={{ span: 14 }}
          layout="horizontal"
          onFinish={onFinish}
          style={{ maxWidth: 600 }}
        >
          <Form.Item label="主体类型" name={"objectType"}>
            <Select options={ops}></Select>
          </Form.Item>
          <Form.Item label="主体" name={"objectName"}>
            <Input />
          </Form.Item>
          <Form.Item label="客体类型" name={"subjectType"}>
            <Select options={ops}></Select>
          </Form.Item>
          <Form.Item label="客体" name={"subjectName"}>
            <Input />
          </Form.Item>
          <Form.Item label="权限" name={"permission"}>
            <Input />
          </Form.Item>
          <Form.Item label="操作">
            <Button type={"primary"} htmlType="submit"> 校验 </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>;
  }

  function onSubjectTypeChange(subjectType:string) {
    setCurrentSelectSubjectType(subjectType)
  }

  function getChildren(model : Model) {

    return <div style={{ display: 'flex', flexDirection: 'row' }}>
      <Card style={{ width: 200, flex: 1 }} title={"关系校验"}>
        <Form
          labelCol={{ span: 4 }}
          wrapperCol={{ span: 14 }}
          layout="horizontal"
          onFinish={onFinish}
          style={{ maxWidth: 600 }}
        >
          <Form.Item label="主体类型" name="objectType" rules={[{ required: true, message: '请选择主体类型!' }]}>
            <Select options={ops}></Select>
          </Form.Item>
          <Form.Item label="主体" name={"objectName"} rules={[{ required: true, message: '请输入主体名称!' }]}>
            <Input />
          </Form.Item>
          <Form.Item label="客体类型" name={"subjectType"} rules={[{ required: true, message: '请选择客体类型!' }]}>
            <Select options={ops} onChange={onSubjectTypeChange}></Select>
          </Form.Item>
          <Form.Item label="客体" name={"subjectName"} rules={[{ required: true, message: '请输入客体名称!' }]}>
            <Input />
          </Form.Item>
          <Form.Item label="关系" name={"relation"} rules={[{ required: true, message: '请输入关系!' }]}>
            <Select options={relationOps}></Select>
          </Form.Item>
          <Form.Item label="操作">
            <Button type={"primary"} htmlType="submit"> 校验 </Button>
          </Form.Item>
        </Form>
      </Card>
      <Card style={{ width: 200, flex: 1 }} title={"权限校验"}>
        <Form
          labelCol={{ span: 4 }}
          wrapperCol={{ span: 14 }}
          layout="horizontal"
          onFinish={onFinish}
          style={{ maxWidth: 600 }}
        >
          <Form.Item label="主体类型" name={"objectType"} rules={[{ required: true, message: '请选择主体类型!' }]}>
            <Select options={ops}></Select>
          </Form.Item>
          <Form.Item label="主体" name={"objectName"} rules={[{ required: true, message: '请输入主体!' }]}>
            <Input />
          </Form.Item>
          <Form.Item label="客体类型" name={"subjectType"} rules={[{ required: true, message: '请选择客体类型!' }]}>
            <Select options={ops} onChange={onSubjectTypeChange}></Select>
          </Form.Item>
          <Form.Item label="客体" name={"subjectName"} rules={[{ required: true, message: '请输入客体!' }]}>
            <Input />
          </Form.Item>
          <Form.Item label="权限" name={"permission"} rules={[{ required: true, message: '请输入要校验的权限!' }]}>
            <Select options={permissionOps}></Select>
          </Form.Item>
          <Form.Item label="操作">
            <Button type={"primary"} htmlType="submit"> 校验 </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>;
  }

  return (
    <div>
      {contextHolder}
      <Space direction="vertical" style={{ width: '100%' }} size={[0, 48]}>
        <Layout>
          <Header style={headerStyle}>
            <Selector handleOpsChange={handleSelectChange} ops={stores.map(item => ({value: item.id.toString(), label: item.name, desc: item.description}))}></Selector>
          </Header>
          <Content style={contentStyle}>
            {models.length == 0 ? getDemo() :
              <Tabs defaultActiveKey="1"
                    items={models.map(item => ({key: item.id, label: item.name, children:  getChildren(item)}))}
                    onChange={onTabChange} />}
          </Content>
          <Footer style={footerStyle}>SimpleReBac权限演示中台</Footer>
        </Layout>

      </Space>
    </div>
  );

}

export default App
