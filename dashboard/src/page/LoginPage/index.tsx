import React, {useEffect, useState} from 'react';
import type { FormProps } from 'antd';
import {Button, Card, Checkbox, Form, Input, notification} from 'antd';
import "./style.less"
import {login, register} from "../../request/api";
import Loading from "../../components/common/Loading";

type FieldType = {
  username?: string;
  password?: string;
  remember?: string;
};

type RegisterType = {
  username?: string;
  password?: string;
};



const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
  console.log('Failed:', errorInfo);
};

const App: React.FC = () => {
  const [api, contextHolder] = notification.useNotification();

 const [mode, setMode] = useState<string>("login")
  const onLoginFinish: FormProps<FieldType>['onFinish'] = (values) => {
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
            window.location.href = "/homepage"
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
  const onRegisterFinish: FormProps<RegisterType>['onFinish'] = (values) =>{
    if(values.username !== undefined && values.password !== undefined) {
      api.open({
        key: "register",
        message: '注册中',
        description:
          <Loading></Loading>
      });
      register({
        username:values.username,
        password:values.password
      }).then(r => {
        if(r.msg === "success") {
          api.open({
            key: "register",
            message: '注册成功,已自动登陆',
            description:
              <Loading></Loading>
          });
          localStorage.setItem("token", r.data.token)
          setTimeout(() => {
            window.location.href = "/homepage"
          }, 1000);
        } else {
          api.open({
            key: "register",
            message: '提示',
            description: r.msg
          });
        }
      })
    }
  }

  useEffect(() => {
    api.open({
      key: "login",
      message: '提示',
      description: <div>
        <p>请登录</p>
      </div>,
    });
  }, [api])

  function setModeRegister() {
    api.open({
      key: "register",
      message: '提示',
      description: <div>
        <p>正在跳转注册</p>
        <Loading></Loading>
      </div>,
    });
    setTimeout(() => {
      setMode("register")
      api.open({
        key: "register",
        message: '提示',
        description: <div>
          <p>请注册</p>
        </div>,
      });
    }, 1500);
  }

  function getLoginForm() {
    return <Card title="登陆" className="login-card ">
      <Form
        name="basic"
        labelCol={{span: 8}}
        wrapperCol={{span: 16}}
        style={{maxWidth: 600}}
        initialValues={{remember: true}}
        onFinish={onLoginFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item<FieldType>
          label="用户名"
          name="username"
          rules={[{required: true, message: 'Please input your username!'}]}
        >
          <Input/>
        </Form.Item>

        <Form.Item<FieldType>
          label="密码"
          name="password"
          rules={[{required: true, message: 'Please input your password!'}]}
        >
          <Input.Password/>
        </Form.Item>

        <Form.Item<FieldType>
          name="remember"
          valuePropName="checked"
          wrapperCol={{offset: 8, span: 16}}
        >
          <Checkbox>记住我</Checkbox>
        </Form.Item>

        <Form.Item wrapperCol={{offset: 8, span: 16}}>
          <Button className="btn" type="primary" htmlType="submit">
            登陆
          </Button>
        </Form.Item>
        <Form.Item<FieldType>
          name="remember"
          valuePropName="checked"
          wrapperCol={{offset: 6, span: 16}}>
          <div>还没有账号？<a onClick={() => setModeRegister()}>点击注册</a></div>
        </Form.Item>
      </Form>
    </Card>
  }



  function getRegisterForm() {
    return <Card title="注册" className="login-card "><Form
      name="basic"
      labelCol={{span: 8}}
      wrapperCol={{span: 16}}
      style={{maxWidth: 600}}
      initialValues={{remember: true}}
      onFinish={onRegisterFinish}
      onFinishFailed={onFinishFailed}
      autoComplete="off"
    >
      <Form.Item<RegisterType>
        label="用户名"
        name="username"
        rules={[{required: true, message: 'Please input your username!'}]}
      >
        <Input/>
      </Form.Item>

      <Form.Item<RegisterType>
        label="密码"
        name="password"
        rules={[{required: true, message: 'Please input your password!'}]}
      >
        <Input.Password/>
      </Form.Item>

      <Form.Item wrapperCol={{offset: 8, span: 16}}>
        <Button className="btn" type="primary" htmlType="submit">
          注册
        </Button>
      </Form.Item>
    </Form></Card>;
  }

  return (
  <div className="slick-login ">
    {contextHolder}
    { mode === "login" ? getLoginForm() : getRegisterForm() }
  </div>
);}

export default App;
