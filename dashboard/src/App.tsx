import React, {useState} from 'react';
import Graph from "./components/graph";
import {Layout, Menu, MenuProps, Space, Tabs, TabsProps} from "antd";
import {Content, Footer, Header} from "antd/es/layout/layout";
import {AppstoreOutlined, MailOutlined, SettingOutlined} from "@ant-design/icons";
import Selector from "./components/Selector";

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

const onChange = (key: string) => {
    console.log(key);
};

const items: TabsProps['items'] = [
    {
        key: '1',
        label: `Tab 1`,
        children: (
            <>
                <Graph></Graph>
            </>
        ),
    },
    {
        key: '2',
        label: `Tab 2`,
        children: `Content of Tab Pane 2`,
    },
    {
        key: '3',
        label: `Tab 3`,
        children: `Content of Tab Pane 3`,
    },
];

function App() {

    const [current, setCurrent] = useState('mail');

    const onClick: MenuProps['onClick'] = (e) => {
        console.log('click ', e);
        setCurrent(e.key);
    };

  return (
    <div>
        <Space direction="vertical" style={{ width: '100%' }} size={[0, 48]}>
            <Layout>
                <Header style={headerStyle}>
                    <Selector></Selector>
                </Header>
                <Content style={contentStyle}>
                    <Tabs defaultActiveKey="1" items={items} onChange={onChange} />
                </Content>
                <Footer style={footerStyle}>ReBac权限演示中台</Footer>
            </Layout>
        </Space>
    </div>
  );
}

export default App;
