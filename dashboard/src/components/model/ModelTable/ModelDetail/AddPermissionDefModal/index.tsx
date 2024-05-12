import React, {useState} from 'react';
import {Button, Form, Input, message, Modal, Space} from 'antd';
import Script from "../../../../script";

interface PropsType {
  isModalOpen:boolean
  definition:Definition | undefined
  handleOk:()=>void
  handleCancel:()=>void
  updateDefinition:(definition:Definition)=>void
}

interface FormType {
  permission:string,
  relationCanAccess:string,
}

const App: React.FC<PropsType> = (props:PropsType) => {
  const [script,setScript] = useState<string>();

  if(props.definition === undefined) {
    return <></>
  }

  const onFinish = (formValue: FormType) => {
    let definifion = props.definition

    if(formValue.permission === undefined || formValue.relationCanAccess === undefined
    || formValue.permission === "" || formValue.relationCanAccess === "") {
      message.info("请输入权限名称/关系表达式")
      return
    }

    if(definifion !== undefined) {

      if(definifion.permissions === null) {
        definifion.permissions = []
      }
      console.log(script)
      definifion.permissions.push({
        permission:formValue.permission,
        relationCanAccess:formValue.relationCanAccess,
        script:script
      })
      props.updateDefinition(definifion)
    }

    props.handleOk()
  };


  return (
    <>
      <Modal title="增加权限定义" open={props.isModalOpen}
             onOk={props.handleOk}
             onCancel={props.handleCancel}
             width={1000}
             footer={null} >
        <Form
          labelCol={{ flex: '200px' }}
          labelAlign="left"
          labelWrap
          wrapperCol={{ flex: 1 }}
          colon={false}
          style={{ width: '100%' }}
          onFinish={onFinish}
        >
          <Form.Item label="权限名称" name="permission">
          <Input />
        </Form.Item>
          <Form.Item label="有权限的关系（表达式）" name="relationCanAccess">
            <Input />
          </Form.Item>
          <Form.Item label="表达式" name="script" style={{ width: '100%',alignItems: 'center'  }}>
            <Script onChange={(script) => setScript(script)} type={"java"}></Script>
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
