
import React, {useEffect, useState} from 'react';
import {Layout, message, notification, Space, Tabs} from "antd";
import {Content, Footer, Header} from "antd/es/layout/layout";
import Selector from "../Selector";
import Relation from "../Relation"
import {getAllModel, getAllStore} from "../../../request/api";

const headerStyle: React.CSSProperties = {
    textAlign: 'center',
    color: '#fff',
    height: 64,
    paddingInline: 50,
    lineHeight: '64px',
    backgroundColor: '#7dbcea',
};

const contentStyle: React.CSSProperties = {
    textAlign: 'center',
    minHeight: 120,
    lineHeight: '120px',
    color: '#fff',
    backgroundColor: '#f0f2f3',
};

const footerStyle: React.CSSProperties = {
    textAlign: 'center',
    color: '#fff',
    backgroundColor: '#7dbcea',
};

const siderStyle: React.CSSProperties = {
  textAlign: 'center',
  lineHeight: '120px',
  color: '#fff',
  backgroundColor: '#1677ff',
};


function App() {
    const [api, contextHolder] = notification.useNotification();
    //权限model
    const [models, setModels] = useState<Model[]>([])
    //存储模型
    const [stores, setStores] = useState<Store[]>([]);
    //当前选择tab
    const [selectTab, setSelectTab] = useState<string>();
    //权限策略
    const [policy, setPolicy] = useState<Policy>();

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
                } else {
                  api.open({
                    message: '提示',
                    description:
                      '该存储空间未创建权限模型，请前往【模型管理】创建权限模型。',
                    duration: 0,
                  });
                }
            }
        })
    }
    const onTabChange = (key: string) => {
        setSelectTab(key)
    };


  return (
        <div>
          {contextHolder}
            <Space direction="vertical" style={{ width: '100%' }} size={[0, 48]}>
                <Layout>
                    <Header style={headerStyle}>
                      <Selector handleOpsChange={handleSelectChange} ops={stores.map(item => ({value: item.id.toString(), label: item.name, desc: item.description}))}></Selector>
                    </Header>
                    <Content style={contentStyle}>
                      {models.length == 0 ? <Relation isEmpty={true} selectModel={selectTab} storeId={0} policyId={""}></Relation> : <Tabs defaultActiveKey="1"
                                                          items={models.map(item => ({key: item.id, label: item.name, children:  <Relation isEmpty={false} selectModel={selectTab} storeId = {item.storeId} policyId={item.policyId}></Relation>}))}
                                                          onChange={onTabChange} />}
                    </Content>
                    <Footer style={footerStyle}>SimpleReBac权限演示中台</Footer>
                </Layout>

            </Space>
        </div>
    );

}

export default App
