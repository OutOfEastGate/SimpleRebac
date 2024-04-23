
import React, {useEffect, useState} from 'react';
import {Button, Card, Col, Layout, message, Row, Space, Statistic, Tabs} from "antd";
import {Content, Footer, Header} from "antd/es/layout/layout";
import {getAllModel, getAllStore} from "../../request/api";
import Selector from "../graph/Selector";
import Table from "./Table";


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

  //权限model
  const [models, setModels] = useState<Model[]>([])
  //存储模型
  const [stores, setStores] = useState<Store[]>([]);
  //当前选择tab
  const [selectTab, setSelectTab] = useState<string>();
  //权限策略
  const [policy, setPolicy] = useState<Policy>();

  useEffect(() => {
    console.log("getAppKey")
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
        setSelectTab(res.data[0].id)
      }
    })
  }
  const onTabChange = (key: string) => {
    setSelectTab(key)
  };


  return (
    <div>
      <Space direction="vertical" style={{ width: '100%' }} size={[0, 48]}>
        <Card>
          <Row gutter={16}>
            <Col span={8}>
              <Statistic title="应用数量" value={10} />
            </Col>
            <Col span={8}>
              <Statistic title="当前应用实体数量" value={12}  />
            </Col>
            <Col span={8}>
              <Statistic title="鉴权次数" value={12}  />
            </Col>
            <Col span={24} style={{ marginTop: 32 }}>
              <Statistic title="鉴权成功次数" value={32} />
            </Col>
          </Row>
        </Card>
        <Table></Table>

      </Space>
    </div>
  );

}

export default App
