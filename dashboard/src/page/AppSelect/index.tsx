import React, {useEffect, useState} from 'react';
import {Card, List, Image, message, Button, notification} from 'antd';
import {getAppList} from "../../request/api";
import Loading from "../../components/common/Loading";


const App: React.FC = () => {
  const [appList, setAppList] = useState<AppKey[]>([])
  const [api, contextHolder] = notification.useNotification();
  useEffect(()=> {
    getAppList().then(r => {
      if(r.msg === "success") {
        setAppList(r.data)
      } else {
        message.error(r.msg)
      }
    })
  },[])

  function selectApp(item:AppKey) {
    localStorage.setItem("appKey", item.appKey)
    api.open({
      key: "login",
      message: '提示',
      description: <div>
        <p>正在进入应用</p>
        <Loading></Loading>
      </div>,
    });
    setTimeout(() => {
      window.location.href = "/homepage"
    }, 2000);
  }

  return (
    <div>
      {contextHolder}
      <List
        grid={{
          gutter: 16,
          xs: 1,
          sm: 2,
          md: 4,
          lg: 4,
          xl: 6,
          xxl: 3,
        }}
        dataSource={appList}
        renderItem={(item) => (
          <List.Item>
            <Card title={item.appName}>
              <Image
                width={200}
                src={`${item.appIcon}?x-oss-process=image/blur,r_50,s_50/quality,q_1/resize,m_mfit,h_200,w_200`}
              /><br/>
              <Button type={"primary"} onClick={() => selectApp(item)}> 进入应用 </Button>
            </Card>
          </List.Item>
        )}
      />
    </div>
  )
};

export default App;
