import React, { useState } from 'react';
import {Button, Checkbox, Form, FormProps, Input, Modal, Space} from 'antd';
const tailLayout = {
  wrapperCol: { offset: 8, span: 16 },
};
interface PropsType{
  showModal: boolean,
  confirmEdge: (param:AddRelationType) => void
  cancelEdge: (param:string) => void
}


const App: React.FC<PropsType> = (props) => {

  const handleCancel = () => {
    props.cancelEdge("aaa")
  };

  const onFinish: FormProps<AddRelationType>["onFinish"] = (values) => {
    props.confirmEdge({
      relation: values.relation,
      isPenetrate: values.isPenetrate
    })
    console.log('Success:', values);
  };

  const onFinishFailed: FormProps<AddRelationType>["onFinishFailed"] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

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
            rules={[{ required: true, message: '请输入关系名称!' }]}
          >
            <Input />
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
