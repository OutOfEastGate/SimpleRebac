import React from 'react';
import type { FormProps } from 'antd';
import {Button, Card, Checkbox, Form, Input, message, notification} from 'antd';
import "./style.less"
import {login} from "../../request/api";
import Loading from "../../components/common/Loading";
import {useNavigate} from "react-router-dom";

type FieldType = {
  username?: string;
  password?: string;
  remember?: string;
};



const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
  console.log('Failed:', errorInfo);
};

const App: React.FC = () => {
  const [api, contextHolder] = notification.useNotification();

  const navigateTo = useNavigate()
  const onFinish: FormProps<FieldType>['onFinish'] = (values) => {
    if(values.username !== undefined && values.password !== undefined) {
      api.open({
        key: "login",
        message: '登陆中',
        description:
          <Loading></Loading>
      });
      login({
        username:values.username,
        password:values.password
      }).then(r => {
        if(r.msg === "success") {
          localStorage.setItem("token", r.data.token)
          api.open({
            key: "login",
            message: '提示',
            description: <div>
              <p>登陆成功，正在跳转</p>
              <Loading></Loading>
            </div>,
          });
          setTimeout(() => {
            navigateTo("/homepage")
          }, 2000);
        } else {
          api.open({
            key: "login",
            message: '提示',
            description: r.msg,
          });
        }
      })

    }
  };
  return (
  <div className="slick-login ">
    {contextHolder}
    <Card title="登陆" className="login-card ">
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
        <Form.Item<FieldType>
          label="用户名"
          name="username"
          rules={[{ required: true, message: 'Please input your username!' }]}
        >
          <Input />
        </Form.Item>

        <Form.Item<FieldType>
          label="密码"
          name="password"
          rules={[{ required: true, message: 'Please input your password!' }]}
        >
          <Input.Password />
        </Form.Item>

        <Form.Item<FieldType>
          name="remember"
          valuePropName="checked"
          wrapperCol={{ offset: 8, span: 16 }}
        >
          <Checkbox>记住我</Checkbox>
        </Form.Item>

        <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
          <Button className="btn"  type="primary" htmlType="submit">
            登陆
          </Button>
        </Form.Item>
      </Form>
    </Card>
  </div>
);}

export default App;
