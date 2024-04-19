import React from 'react';
import {Button, Descriptions, Space, Table} from 'antd';
import type { TableColumnsType, TableProps } from 'antd';
import {PlusOutlined} from "@ant-design/icons";


interface PropsType {
  data: Definition[]
}

interface TableDefinitionType {
  key: string,
  description:string
  objectType:string
  permissions:PermissionDefinition[]
  relations:RelationDefinition[]
  children?: ChildrenType
}

interface ChildrenType {
  permissions:PermissionDefinition[]
  relations:RelationDefinition[]
}




const onChange: TableProps<Definition>['onChange'] = (pagination, filters, sorter, extra) => {
  console.log('params', pagination, filters, sorter, extra);
};

const App: React.FC<PropsType> = (props) => {
  function openDefinitionDetail(record: TableDefinitionType) {

  }

  const columns: TableColumnsType<TableDefinitionType> = [
    {
      title: '描述',
      dataIndex: 'description',
      width: '30%',
    },
    {
      title: '对象类型',
      dataIndex: 'objectType',
      width: '30%',
    },
    {
      title: '权限个数',
      dataIndex: 'permissions',
      width: '15%',
      render: (text) => <p>{text == undefined ? 0 : text.length}</p>,
    },
    {
      title: '关系个数',
      dataIndex: 'relations',
      width: '15%',
      render: (text) => <p>{text == undefined ? 0 : text.length}</p>,
    },
    {
      title: '操作',
      key: 'action',
      width: '10%',
      render: (_, record) => (
        <Space size="middle">
          {/*<Button type={"primary"} onClick={() => {*/}
          {/*  openDefinitionDetail(record)}}>查看权限定义</Button>*/}
          {/*<Button type={"primary"} onClick={() => {*/}
          {/*  openDefinitionDetail(record)}}>查看关系定义</Button>*/}
          <Button danger={true}>删除定义</Button>
        </Space>
      ),
    },
  ];


  function getDataSource(data: TableDefinitionType[]) {
    if(data === undefined || data === null) {
      return []
    }
    data.map(item => {
      item.key = item.objectType
    })
    return data;
  }

  function deleteRelation(relation: RelationDefinition) {
    console.log("删除关系", relation)
  }

  function deletePermission(permission: PermissionDefinition) {
    console.log("删除权限", permission)
  }

  function getDescriptions(record:TableDefinitionType) {
    let res:JSX.Element[] = []
    let permissions = record.permissions

    if(permissions != null) {
      permissions.forEach(permission => {
        res.push(
          <Descriptions title={<div>权限定义 <Button danger={true} onClick={() => deletePermission(permission)}>删除权限</Button></div>}>
            <Descriptions.Item label="权限">{permission.permission}</Descriptions.Item>
            <Descriptions.Item label="有权限的角色">{permission.relationCanAccess}</Descriptions.Item>
        </Descriptions>)
      })
    }
    res.push(<Button
      type="dashed"
      style={{ width: 120 }}
      icon={<PlusOutlined />}
    >
      增加权限
    </Button>)

    let relations = record.relations

    if(relations != null) {
      relations.forEach(relation => {
        res.push(
          <Descriptions title={<div>关系定义 <Button danger={true} onClick={() => deleteRelation(relation)}>删除关系</Button></div>}>
          <Descriptions.Item label="关系">{relation.relation}</Descriptions.Item>
          <Descriptions.Item label="可建立关系的角色类型">{relation.subjectType}</Descriptions.Item>
        </Descriptions>)
      })
    }
    res.push(<Button
      type="dashed"
      style={{ width: 120 }}
      icon={<PlusOutlined />}
    >
      增加关系
    </Button>)

    if(permissions == null && relations == null) {
      res.push(<div> 暂无权限和关系定义 </div>)
    }

    return <Space direction="vertical">{res}</Space>;
  }

  return <Table columns={columns}
                expandable={{ expandedRowRender: (record) => getDescriptions(record) }}
                dataSource={getDataSource(props.data)}
                onChange={onChange} />
};

export default App;
