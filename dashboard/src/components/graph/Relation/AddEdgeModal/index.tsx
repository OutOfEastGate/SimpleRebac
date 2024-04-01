import React from 'react';
import {Button, Checkbox, Form, FormProps, Modal, Select, Space} from 'antd';
import {Edge, Node} from "@antv/x6";
const tailLayout = {
  wrapperCol: { offset: 8, span: 16 },
};
interface PropsType{
  showModal: boolean,
  edge: Edge | undefined,
  sourceNode: Node | undefined,
  targetNode: Node | undefined,
  policy:Policy | undefined,
  confirmEdge: (param:AddRelationType) => void
  cancelEdge: () => void
}


const App: React.FC<PropsType> = (props) => {

  const handleCancel = () => {
    props.cancelEdge()
  };

  const onFinish: FormProps<AddRelationType>["onFinish"] = (values) => {
    props.confirmEdge({
      relation: values.relation,
      isPenetrate: values.isPenetrate
    })
  };

  const onFinishFailed: FormProps<AddRelationType>["onFinishFailed"] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };



  const getOps = () => {
    if(props.policy === undefined || props.policy.definitions === undefined) {
      return []
    }
    let edge = props.edge
    let target = edge?.getTarget()
    let source = edge?.getSource()
    if(target === undefined || source === undefined) {
      return []
    }

    let definitions = props.policy.definitions
    let def: Definition | undefined

    if ("cell" in target && "cell" in source) {
      let targetNode = props.targetNode
      let sourceNode = props.sourceNode
      if(targetNode !== undefined && sourceNode !== undefined) {
        let targetObjectType = targetNode.attrs?.objectType?.objectType
        let sourceObjectType = sourceNode.attrs?.objectType?.objectType
        definitions.forEach(definition => {
          if(definition.objectType === targetObjectType) {
            def = definition
          }
        })

        if(def?.relations === undefined || def?.relations === null) {
          return []
        }

        let relations = def.relations.filter(relation => {
          if(relation.subjectType === sourceObjectType) {
            return relation
          }
        })

        if(def !== undefined && relations !== null) {
          return relations.map(relation => {
            return {
              value: relation.relation,
              label: relation.relation,
              desc: relation.subjectType,
            }
          })
        }
      }

    }

    return []
  }

  return (
    <>
      <Modal title="设置关系" open={props.showModal} onCancel={handleCancel} footer={null}>
        <Form
          name="basic"
          labelCol={{ span: 8 }}
          wrapperCol={{ span: 16 }}
          style={{ maxWidth: 600 }}
          initialValues={{ remember: true }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
          autoComplete="off"
        >
          <Form.Item<AddRelationType>
            label="关系"
            name="relation"
            rules={[{ required: true, message: '请选择关系名称!' }]}
          >
            <Select
              showSearch
              style={{ width: 200 }}
              placeholder="选择存储模型"
              optionFilterProp="children"

              filterOption={(input, option) => (option?.value ?? '').includes(input)}
              filterSort={(optionA, optionB) =>
                (optionA?.value ?? '').toLowerCase().localeCompare((optionB?.value ?? '').toLowerCase())
              }
              options={getOps()}
            >
            </Select>
          </Form.Item>

          <Form.Item<AddRelationType>
            name="isPenetrate"
            valuePropName="checked"
            wrapperCol={{ offset: 8, span: 16 }}
          >
            <Checkbox>是否穿透</Checkbox>
          </Form.Item>

          <Form.Item {...tailLayout}>
            <Space>
              <Button onClick={handleCancel} >
                取消
              </Button>
              <Button type="primary" htmlType="submit">
                确认
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default App;
