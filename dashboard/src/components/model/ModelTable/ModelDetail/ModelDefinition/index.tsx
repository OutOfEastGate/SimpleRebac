import React, {useEffect, useState} from 'react';
import {Button, Descriptions, Space, Table} from 'antd';
import type { TableColumnsType, TableProps } from 'antd';
import {PlusOutlined} from "@ant-design/icons";
import AddPermissionDefModal from "../AddPermissionDefModal";
import AddRelationDefModal from "../AddRelationDefModal";
import AddDefinitionDefModal from "../AddDefinitionDefModal";


interface PropsType {
  policy: Policy
  data: Definition[]
  updateDefinition: (definition:Definition) => void
  addDefinition: (definition:Definition) => void
  deleteDefinition: (definition:Definition) => void
}

interface TableDefinitionType {
  policyId:string,
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
          <Button danger={true} onClick={() => deleteDefinition(record)}>删除定义</Button>
        </Space>
      ),
    },
  ];
  const [data, setData] = useState(props.data);
  const [isPermissionModalOpen, setIsPermissionModalOpen] = useState<boolean>(false)
  const [isRelationModalOpen, setIsRelationModalOpen] = useState<boolean>(false)
  const [isAddDefinitionOpen, setIsAddDefinitionOpen] = useState<boolean>(false)
  const [currentDefinition, setCurrentDefinition] = useState<TableDefinitionType>()
  const [currentDelete, setCurrentDelete] = useState({})

  useEffect(() => {
    setData(props.data);
  }, [props.data]);

  function getDataSource(data: TableDefinitionType[]) {
    if(data === undefined || data === null) {
      return []
    }
    data.map(item => {
      item.key = item.objectType
    })
    return data;
  }

  function deleteRelation(record:Definition, relation: RelationDefinition) {
    if(record !== undefined) {
      let newDefinition = record
      let newRelations: RelationDefinition[] = []
      record.relations.forEach(curRelation => {
         if(!(curRelation.relation == relation.relation && curRelation.subjectType == relation.subjectType)) {
           newRelations.push(curRelation)
         }
      })
      newDefinition.relations = newRelations
      updateDefinition(newDefinition)
      setCurrentDelete(relation)
    }
  }

  function deletePermission(record:Definition, permission: PermissionDefinition) {
    if(record !== undefined) {
      let newDefinition = record
      let newPermissions: PermissionDefinition[] = []
      record.permissions.forEach(curPermission => {
        if(!(curPermission.permission == permission.permission && curPermission.relationCanAccess == permission.relationCanAccess)) {
          newPermissions.push(curPermission)
        }
      })
      newDefinition.permissions = newPermissions
      setCurrentDelete(permission)
      updateDefinition(newDefinition)
    }
  }

  function addPermissionDef(record:TableDefinitionType) {
    setCurrentDefinition(record)
    setIsPermissionModalOpen(true)
  }

  function addRelationDef(record: TableDefinitionType) {
    setCurrentDefinition(record)
    setIsRelationModalOpen(true)
  }

  function getDescriptions(record:TableDefinitionType) {
    let res:JSX.Element[] = []
    let permissions = record.permissions

    if(permissions != null) {
      permissions.forEach(permission => {
        res.push(
          <Descriptions title={<div>权限定义 <Button danger={true} onClick={() => deletePermission(record, permission)}>删除权限</Button></div>}>
            <Descriptions.Item label="权限">{permission.permission}</Descriptions.Item>
            <Descriptions.Item label="有权限的关系">{permission.relationCanAccess}</Descriptions.Item>
        </Descriptions>)
      })
    }
    res.push(<Button
      type="dashed"
      style={{ width: 120 }}
      icon={<PlusOutlined />}
      onClick={() => addPermissionDef(record)}
    >
      增加权限
    </Button>)

    let relations = record.relations

    if(relations != null) {
      relations.forEach(relation => {
        res.push(
          <Descriptions title={<div>关系定义 <Button danger={true} onClick={() => deleteRelation(record, relation)}>删除关系</Button></div>}>
          <Descriptions.Item label="关系">{relation.relation}</Descriptions.Item>
          <Descriptions.Item label="可建立关系的角色类型">{relation.subjectType}</Descriptions.Item>
        </Descriptions>)
      })
    }
    res.push(<Button
      type="dashed"
      style={{ width: 120 }}
      icon={<PlusOutlined />}
      onClick={() => addRelationDef(record)}
    >
      增加关系
    </Button>)

    if(permissions == null && relations == null) {
      res.push(<div> 暂无权限和关系定义 </div>)
    }

    return <Space direction="vertical">{res}</Space>;
  }



  return (
    <div>
      <AddPermissionDefModal isModalOpen={isPermissionModalOpen} definition={currentDefinition} handleOk={handleOk} handleCancel={handleCancel} updateDefinition={updateDefinition}></AddPermissionDefModal>
      <AddRelationDefModal isModalOpen={isRelationModalOpen} definition={currentDefinition} handleOk={handleOk} handleCancel={handleCancel} updateDefinition={updateDefinition}></AddRelationDefModal>
      <AddDefinitionDefModal isModalOpen={isAddDefinitionOpen} handleOk={handleOk} handleCancel={handleCancel} addDefinition={addDefinition}></AddDefinitionDefModal>
      <Button type="primary" onClick={() => {setIsAddDefinitionOpen(true)}}> 增加定义 </Button>
      <Table columns={columns}
             expandable={{ expandedRowRender: (record) => getDescriptions(record) }}
             dataSource={getDataSource(data)}
             onChange={onChange} />
    </div>
  )

  //回调函数
  function handleOk() {
    setIsRelationModalOpen(false)
    setIsPermissionModalOpen(false)
    setIsAddDefinitionOpen(false)
  }

  function handleCancel() {
    setIsRelationModalOpen(false)
    setIsPermissionModalOpen(false)
    setIsAddDefinitionOpen(false)
  }

  function updateDefinition(definition:Definition) {
    props.updateDefinition(definition)
  }

  function addDefinition(definition:AddDefinitionType) {
    let newDefinition:Definition = {
      policyId:props.policy.id,
      key: definition.objectType,
      description:definition.description,
      objectType:definition.objectType,
      permissions:[],
      relations:[]
    }
    setCurrentDelete(newDefinition)
    props.addDefinition(newDefinition)
  }

  function deleteDefinition(definition:Definition) {
    props.deleteDefinition(definition)
  }
};

export default App;
