import React from "react";
import {Button, Form, FormProps, Input, Modal} from "antd";
import TextArea from "antd/es/input/TextArea";

const tailLayout = {
  wrapperCol: { offset: 8, span: 16 },
};
interface PropsType{
  showModal:boolean
  handleCancel: () => void
  handleAddModel: (addModel:AddModelType) => void
}




const App: React.FC<PropsType> = (props) => {
  function handleCancel() {
    props.handleCancel()
  }

  const onFinish: FormProps<AddModelType>["onFinish"] = (values) => {
    props.handleAddModel(values)
  };

  const onFinishFailed: FormProps<AddModelType>["onFinishFailed"] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  return (
    <>
      <Modal title="添加模型" open={props.showModal} onCancel={handleCancel} footer={null}>
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
          <Form.Item label="模型名称" name="name" rules={[{ required: true, message: '请输入关系名称!' }]}>
            <Input></Input>
          </Form.Item>
          <Form.Item label="模型描述" name="description">
            <TextArea rows={4} />
          </Form.Item>
          <Form.Item label="" {...tailLayout}>
            <Button type="primary" htmlType="submit"> 确定 </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  )
}

export default App
