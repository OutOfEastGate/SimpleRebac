import React from 'react';
import {Button, Form, Input, message, Modal, Space} from 'antd';

interface PropsType {
  isModalOpen:boolean
  handleOk:()=>void
  handleCancel:()=>void
  addDefinition:(definition:AddDefinitionType)=>void
}

interface FormType {
  description:string,
  objectType:string
}

const App: React.FC<PropsType> = (props:PropsType) => {


  const onFinish = (formValue: FormType) => {
    props.addDefinition({
      description: formValue.description,
      objectType: formValue.objectType
    })
    props.handleOk()
  };


  return (
    <>
      <Modal title="增加定义" open={props.isModalOpen} onOk={props.handleOk} onCancel={props.handleCancel} footer={null} >
        <Form
          labelCol={{ flex: '130px' }}
          labelAlign="left"
          labelWrap
          wrapperCol={{ flex: 1 }}
          colon={false}
          style={{ maxWidth: 600 }}
          onFinish={onFinish}
        >
          <Form.Item label="定义描述" name="description">
            <Input />
          </Form.Item>
          <Form.Item label="角色类型（唯一）" name="objectType">
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
