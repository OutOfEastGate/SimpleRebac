import React, {useEffect, useState} from 'react';
import Graph from "../graph";
import {Layout, Menu, MenuProps, Space, Tabs, TabsProps, Tooltip} from "antd";
import {Content, Footer, Header} from "antd/es/layout/layout";
import Selector from "../Selector";
import {getAllModel, getAllStore, getPolicy, getRelation} from "../../request/api";

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
        if (stores.length === 0) {
            getAllStore().then((res) => {
                if (res.msg === 'success' && res.data != null) {
                    setStores(res.data);
                }
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
        console.log("Tab变化" + key)
        setSelectTab(key)
    };
    return (
        <div>
            <Space direction="vertical" style={{ width: '100%' }} size={[0, 48]}>
                <Layout>
                    <Header style={headerStyle}>
                        <Selector handleOpsChange={handleSelectChange} ops={stores.map(item => ({value: item.id.toString(), label: item.name, desc: item.description}))}></Selector>
                    </Header>
                    <Content style={contentStyle}>
                        <Tabs defaultActiveKey="1"
                              items={models.map(item => ({key: item.id, label: item.name, children: <Graph selectModel={selectTab}></Graph>}))}
                              onChange={onTabChange} />
                    </Content>
                    <Footer style={footerStyle}>ReBac权限演示中台</Footer>
                </Layout>
            </Space>
        </div>
    );

}

//从policy中解析出对象
function getNodes(relation:Relation[]|undefined):[] {
    return []
}
//从policy中解析出关联关系
function getLinks(policy:Relation[]|undefined):[] {
    return []
}

export default App;
