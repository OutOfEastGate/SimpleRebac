import React, { useState } from 'react';
import {Button, Checkbox, Form, FormProps, Input, Modal, Space} from 'antd';
const tailLayout = {
  wrapperCol: { offset: 8, span: 16 },
};
interface PropsType{
  showModal: boolean,
  confirmNode: (param:AddNodeType) => void
  cancelNode: () => void
}


const App: React.FC<PropsType> = (props) => {

  const handleCancel = () => {
    console.log(111)
    props.cancelNode()
  };

  const onFinish: FormProps<AddNodeType>["onFinish"] = (values) => {
    props.confirmNode({
      label: values.label
    })
    console.log('Success:', values);
  };

  const onFinishFailed: FormProps<AddNodeType>["onFinishFailed"] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  return (
    <>
      <Modal title="设置实体" open={props.showModal} onCancel={handleCancel} footer={null}>
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
            label="实体名称"
            name="label"
            rules={[{ required: true, message: '请输入关系名称!' }]}
          >
            <Input />
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
