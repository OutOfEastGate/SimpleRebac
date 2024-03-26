import React, {useEffect, useState} from 'react';

import {Layout, Menu, MenuProps, Space, Tabs, theme} from "antd";
import {Outlet, useNavigate} from "react-router-dom"


const { Header, Content, Footer, Sider } = Layout;

const items = [
  {
    key: "/homepage",
    label: "首页"
  },
  {
    key: "/relation",
    label: "关系演示中台"
  },
  {
    key: "2",
    label: "存储模型管理"
  },
  {
    key: "/key",
    label: "密钥管理"
  },
  {
    key: "/system",
    label: "系统监控"
  }
]

const App: React.FC = () => {

  const navigateTo = useNavigate()

  const selectMenu = (params: {key:string}) => {
    console.log(params)
    navigateTo(params.key)
  }

  return (
    <Layout hasSider={true}>
      <Sider
        style={{ overflow: 'auto', height: '100vh', position: 'fixed', left: 0, top: 0, bottom: 0 }}
      >
        <div className="demo-logo-vertical" />
        <Menu theme="dark" mode="inline" defaultSelectedKeys={['1']} items={items} onSelect={selectMenu} />
      </Sider>
      <Layout style={{ marginLeft: 200 }}>
        <Outlet></Outlet>
      </Layout>
    </Layout>
  );
};

export default App;
