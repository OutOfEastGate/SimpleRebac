import React, {useEffect, useState} from 'react';
import Script from "../../components/script";
import {Button, Form, notification, Space} from "antd";
import {executeScript} from "../../request/api";
import Loading from "../../components/common/Loading";

interface PropsType {

}

const App: React.FC<PropsType> = (props) => {

  const [script, setScript] = useState<string>()

  const [param, setParam] = useState<string>("")

  const [api, contextHolder] = notification.useNotification();

  function execute() {
    if(script === undefined) {
      api.open({
        key: "execute",
        message: '提示',
        description: '请输入脚本'
      });
      return
    }
    executeScript({groovyScript:script, params:JSON.parse(param)}).then(r => {
      if(r.msg === "success") {
        api.open({
          key: "execute",
          message: '执行成功',
          description: JSON.stringify(r.data)
        });
      } else {
        api.open({
          key: "execute",
          message: '执行失败',
          description: r.msg
        });
      }
    })
  }

  return (
    <>
      <Form>
        { contextHolder }
        <Space direction={"vertical"}>
          {"参数"}
          <Script type={"json"} onChange={(json) => {setParam(json)}}></Script><br/>
          {"脚本"}
          <Script type={"java"} onChange={(script) => {setScript(script)}}></Script><br/>
          <Button onClick={() => execute()}> 运行脚本 </Button>
        </Space>
      </Form>
    </>
  );

};

export default App;
