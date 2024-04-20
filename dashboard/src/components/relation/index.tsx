
import React, {useEffect, useState} from 'react';
import {Button, Card, Form, Input, Layout, message, Space, Tabs} from "antd";
import {Content, Footer, Header} from "antd/es/layout/layout";
import {getAllModel, getAllStore} from "../../request/api";
import Selector from "../graph/Selector";
import Relation from "../graph/Relation";


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

function App() {

  //权限model
  const [models, setModels] = useState<Model[]>([])
  //存储模型
  const [stores, setStores] = useState<Store[]>([]);
  //权限策略
  const [policy, setPolicy] = useState<Policy>();

  const [selectTab, setSelectTab] = useState<string>();

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
      }
    })
  }

  const onTabChange = (key: string) => {
    setSelectTab(key)
  };

  function getDemo() {

    return <div style={{ display: 'flex', flexDirection: 'row' }}>
      <Card style={{ width: 200, flex: 1 }} title={"关系校验"}>
        <Form
          labelCol={{ span: 4 }}
          wrapperCol={{ span: 14 }}
          layout="horizontal"

          style={{ maxWidth: 600 }}
        >
          <Form.Item label="主体类型">
            <Input />
          </Form.Item>
          <Form.Item label="主体">
            <Input />
          </Form.Item>
          <Form.Item label="关系">
            <Input />
          </Form.Item>
          <Form.Item label="客体类型">
            <Input />
          </Form.Item>
          <Form.Item label="客体">
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

          style={{ maxWidth: 600 }}
        >
          <Form.Item label="主体类型">
            <Input />
          </Form.Item>
          <Form.Item label="主体">
            <Input />
          </Form.Item>
          <Form.Item label="权限">
            <Input />
          </Form.Item>
          <Form.Item label="客体类型">
            <Input />
          </Form.Item>
          <Form.Item label="客体">
            <Input />
          </Form.Item>
          <Form.Item label="操作">
            <Button type={"primary"} htmlType="submit"> 校验 </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>;
  }

  function getChildren(model : Model) {

    return <div style={{ display: 'flex', flexDirection: 'row' }}>
      <Card style={{ width: 200, flex: 1 }} title={"关系校验"}>
        <Form
          labelCol={{ span: 4 }}
          wrapperCol={{ span: 14 }}
          layout="horizontal"

          style={{ maxWidth: 600 }}
        >
          <Form.Item label="主体类型">
            <Input />
          </Form.Item>
          <Form.Item label="主体">
            <Input />
          </Form.Item>
          <Form.Item label="关系">
            <Input />
          </Form.Item>
          <Form.Item label="客体类型">
            <Input />
          </Form.Item>
          <Form.Item label="客体">
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

          style={{ maxWidth: 600 }}
        >
          <Form.Item label="主体类型">
            <Input />
          </Form.Item>
          <Form.Item label="主体">
            <Input />
          </Form.Item>
          <Form.Item label="权限">
            <Input />
          </Form.Item>
          <Form.Item label="客体类型">
            <Input />
          </Form.Item>
          <Form.Item label="客体">
            <Input />
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
