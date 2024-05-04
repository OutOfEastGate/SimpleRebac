import React from 'react';
import {Button, Form, Input, Modal, Space} from 'antd';

interface PropsType {
  isModalOpen:boolean
  definition:Definition | undefined
  handleOk:()=>void
  handleCancel:()=>void
  updateDefinition:(definition:Definition)=>void
}

interface FormType {
  relation:string,
  objectTypeCanAccess:string
}

const App: React.FC<PropsType> = (props:PropsType) => {

  if(props.definition === undefined) {
    return <></>
  }

  const onFinish = (formValue: FormType) => {
    let definifion = props.definition
    if(definifion !== undefined) {
      if(definifion.relations === null) {
        definifion.relations = []
      }

      definifion.relations.push({
        relation:formValue.relation,
        subjectType:formValue.objectTypeCanAccess
      })
      props.updateDefinition(definifion)
    }

    props.handleOk()
  };


  return (
    <>
      <Modal title="添加关系定义" open={props.isModalOpen} onOk={props.handleOk} onCancel={props.handleCancel} footer={null}>
        <Form
          labelCol={{ flex: '110px' }}
          labelAlign="left"
          labelWrap
          wrapperCol={{ flex: 1 }}
          colon={false}
          style={{ maxWidth: 600 }}
          onFinish={onFinish}
        >
          <Form.Item label="关系名称" name="relation">
            <Input />
          </Form.Item>
          <Form.Item label="可建立关系的角色类型" name="objectTypeCanAccess">
            <Input />
          </Form.Item>
          <Form.Item label=" " >
            <Space>
              <Button type="primary" htmlType="submit">
                确定
              </Button>
              <Button htmlType="submit" onClick={props.handleCancel}>
                取消
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default App;
