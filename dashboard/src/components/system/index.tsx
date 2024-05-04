import React from "react";
import {Card, Col, CountdownProps, message, Progress, Row, Space, Statistic, Table, Tag} from "antd";
import {getSystemInfo} from "../../request/api";
import Loading from "../common/Loading";
import Column from "antd/es/table/Column";
import ColumnGroup from "antd/es/table/ColumnGroup";

const { Countdown } = Statistic;
interface DataType {
  key: React.Key;
  appKey: string;
  modelId: string;
  policyId: string;
  triple: string;
  hasPermission: boolean;
  useTimeSeconds: string,
  createTimestamp:string
}

const data: DataType[] = [
  {
    key: '1',
    appKey: 'John',
    modelId: 'Brown',
    policyId: "32",
    triple: 'New York No. 1 Lake Park',
    hasPermission: false,
    useTimeSeconds: "1.2",
    createTimestamp: "2024-4-22"
  },
];
interface stateType{
  systemInfo: SystemInfo | null
}
class App extends React.Component<any, stateType>{
  private timerId: NodeJS.Timeout | null = null;
  constructor(props:any) {
    super(props);
    this.state={
      systemInfo: null
    }
  }

  UNSAFE_componentWillMount() {
    getSystemInfo().then(res => {
      this.setState({
        systemInfo: res.data
      })
    }).catch(error => {
      message.error(error.message)
    })
    this.timerId  = setInterval(() => {
      getSystemInfo().then(res => {
        this.setState({
          systemInfo: res.data
        })
      }).catch(error => {
        message.error(error.message)
      })
    }, 1000 * 2)
  }
  componentWillUnmount() {
    if (this.timerId) {
      clearInterval(this.timerId);
    }
  }


  render() {

    return this.state.systemInfo == null ? <><Loading></Loading></>:
      <>
        <Card>
          <Row gutter={16}>
            <Col span={8}>
              <Statistic title="系统运行时间" value={this.state.systemInfo.systemRuntime} />
            </Col>
            <Col span={8}>
              <Statistic title="CPU核心数" value={this.state.systemInfo.cpuNum}  />
            </Col>
            <Col span={8}>
              <Statistic title="CPU线程数数" value={this.state.systemInfo.cpuThreadNum}  />
            </Col>
            <Col span={8}>
              <Statistic title="鉴权次数" value={this.state.systemInfo.permissionInfo.times}  />
            </Col>
            <Col span={8}>
              <Statistic title="鉴权成功次数" value={this.state.systemInfo.permissionInfo.successTimes}  />
            </Col>
            <Col span={8}>
              <Statistic title="鉴权失败次数" value={this.state.systemInfo.permissionInfo.failTimes}  />
            </Col>

            <Col span={24} style={{ marginTop: 32 }}>
              <Statistic title="系统总内存" value={this.state.systemInfo.totalMemory} />
            </Col>
            <Col span={12}>
              {"系统内存使用率"}<br/>
              <Progress type="circle"  percent={this.state.systemInfo.memoryUsage * 100} />
            </Col>
            <Col span={12}>
              {"JVM内存使用率"}<br/>
              <Progress type="circle" percent={this.state.systemInfo.jvmMemoryUsage * 100} />
            </Col>
          </Row>

        </Card>
        <Card title={"请求日志"}>
          <Table dataSource={this.state.systemInfo.logList}>
            <ColumnGroup title="应用信息">
              <Column title="应用ID" dataIndex="appKey" key="appKey" />
              <Column title="模型ID" dataIndex="modelId" key="modelId" />
              <Column title="策略ID" dataIndex="policyId" key="policyId" />
            </ColumnGroup>
            <Column title="三元组" dataIndex="triple" key="triple" />
            <Column title="使用时间" dataIndex="useTimeSeconds" key="useTimeSeconds" />
            <Column
              title="是否成功"
              dataIndex="hasPermission"
              key="hasPermission"
              render={(hasPermission: boolean) => (
                <>
                  {hasPermission ? <Tag color='green'>true</Tag> : <Tag color = 'volcano'>false</Tag>}
                </>
              )}
            />
            <Column title="请求时间" dataIndex="createTimestamp" key="createTimestamp" render={(createTimestamp:string) => {
              const date = new Date(createTimestamp)
              return(<div>
                {`${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日-${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`}
              </div>)
            }}/>
          </Table>
        </Card>

      </>;
  }
}

export default App;
