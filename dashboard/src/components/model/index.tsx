
import React, {useEffect, useState} from 'react';
import { Layout, message, Space } from "antd";
import {Content, Footer, Header} from "antd/es/layout/layout";
import {getAllModel, getAllStore} from "../../request/api";
import Selector from "../graph/Selector";
import ModelTable from "./ModelTable";


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
  lineHeight: '50px',
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

  //权限model
  const [models, setModels] = useState<Model[]>([])
  //存储模型
  const [stores, setStores] = useState<Store[]>([]);
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
      }
    })
  }


  return (
    <div>
      <Space direction="vertical" style={{ width: '100%' }} size={[0, 48]}>
        <Layout>
          <Header style={headerStyle}>
            <Selector handleOpsChange={handleSelectChange} ops={stores.map(item => ({value: item.id.toString(), label: item.name, desc: item.description}))}></Selector>
          </Header>
          <Content style={contentStyle}>
            <ModelTable data={models}></ModelTable>
          </Content>
          <Footer style={footerStyle}>SimpleReBac权限演示中台</Footer>
        </Layout>

      </Space>
    </div>
  );

}

export default App
