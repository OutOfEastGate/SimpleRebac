import React from 'react';
import {Button, Form, FormProps, Input, Modal, Select, Space, Tooltip} from 'antd';
const tailLayout = {
  wrapperCol: { offset: 8, span: 16 },
};
interface PropsType{
  showModal: boolean,
  confirmNode: (param:AddNodeType) => void
  cancelNode: () => void,
  policy: Policy | undefined
}


const App: React.FC<PropsType> = (props) => {

  const handleCancel = () => {
    props.cancelNode()
  };

  const onFinish: FormProps<AddNodeType>["onFinish"] = (values) => {
    props.confirmNode(values)
  };

  const onFinishFailed: FormProps<AddNodeType>["onFinishFailed"] = (errorInfo) => {

  };

  function handleSelectChange() {

  }



  const items = props.policy == undefined ? [
    {
      objectType: "user",
      description: "用户定义"
    }
  ] : props.policy.definitions.map(def => {
    return {
      objectType: def.objectType,
      description: def.description
    }
  })

  const ops = items.map(item => ({
    value: item.objectType,
    label: <div>
      <Tooltip placement="topRight" title={item.description}>
        {item.objectType}
      </Tooltip></div>,
    desc: item.description,
  }))



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
          <Form.Item
            label={"实体类型"}
            name={"objectType"}
            rules={[{ required: true, message: '请选择实体类型!' }]}
          >
            <Select
              showSearch
              style={{ width: 200 }}
              placeholder="选择存储模型"
              optionFilterProp="children"
              onChange={handleSelectChange}
              filterOption={(input, option) => (option?.value ?? '').includes(input)}
              filterSort={(optionA, optionB) =>
                (optionA?.value ?? '').toLowerCase().localeCompare((optionB?.value ?? '').toLowerCase())
              }
              options={ops}
            >
            </Select>
          </Form.Item>
          <Form.Item
            label="实体名称"
            name="objectName"
            rules={[{ required: true, message: '请输入实体名称!' }]}
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
