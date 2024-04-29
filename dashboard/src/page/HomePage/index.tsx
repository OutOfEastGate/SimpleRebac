import {
  CrownOutlined, CustomerServiceOutlined,
  TabletOutlined,
  UserOutlined,
} from '@ant-design/icons';
import {PageContainer, ProConfigProvider, ProLayout} from '@ant-design/pro-components';
import { FloatButton } from 'antd';

import {Outlet, useNavigate} from "react-router-dom";
import React, {useState} from "react";

export default () => {
  let isDarkFromStore = localStorage.getItem("isDark");
  if(isDarkFromStore === null) {
    console.log("sss")
    isDarkFromStore = "false"
  }
  console.log(isDarkFromStore)
  const [isDark, setIsDark] = useState<boolean>(Boolean(isDarkFromStore))
  const navigateTo = useNavigate();

  const path = {
    path: '/',
    routes: [
      {
        path: '/admin',
        name: '管理',
        icon: <CrownOutlined />,
        access: 'canAdmin',
        routes: [
          {
            path: '/admin/homepage',
            name: '首页',
            icon: <CrownOutlined />,
          },
          {
            path: '/admin/appSelect',
            name: '应用列表',
            icon: <CrownOutlined />,
          },
          {
            path: '/admin/space',
            name: '存储空间管理',
            icon: <CrownOutlined />,
          },
          {
            path: '/admin/model',
            name: '模型管理',
            icon: <CrownOutlined />,
          },
          {
            path: '/admin/system',
            name: '系统运行',
            icon: <CrownOutlined />,
          },
        ],
      },
      {
        name: '权限演示中台',
        icon: <TabletOutlined />,
        path: '/show',
        routes: [
          {
            path: '/show/relation',
            name: '关系演示',
            icon: <CrownOutlined />,
          },
          {
            path: '/show/permission',
            name: '权限演示',
            icon: <CrownOutlined />,
          },
        ],
      },
    ],
  }

  return (
    <div
      id="test-pro-layout"
      style={{
        height: '100vh',
      }}
    >
      <FloatButton.Group
        trigger="hover"
        type="primary"
        style={{ right: 94 }}
        icon={<CustomerServiceOutlined />}
      >
        <FloatButton tooltip={<div>设置为黑暗模式</div>}  onClick={() => {
          const newIsDark = !isDark
          localStorage.setItem("isDark", String(newIsDark))
          setIsDark(newIsDark)
        }}/>
      </FloatButton.Group>
      <ProConfigProvider dark={localStorage.getItem("isDark") === "true"}>
        <ProLayout
          menuHeaderRender={() => {return <>当前应用：{localStorage.getItem("appName")} <br/> 当前存储空间：{localStorage.getItem("storeName")}</>}}
          fixSiderbar
          route={ path }
          avatarProps={{
            icon: <UserOutlined />,
            size: 'small',
            title: 'root',
          }}
          menu={{ defaultOpenAll: true }}
          menuItemRender={(item, dom) => (
            <a
              onClick={() => {
                if(item.path !== undefined) {
                  navigateTo(item.path)
                }
              }}
            >
              {dom}
            </a>
          )}
        >
          <PageContainer>
            <Outlet></Outlet>
          </PageContainer>
        </ProLayout>
      </ProConfigProvider>
    </div>
  );
};
