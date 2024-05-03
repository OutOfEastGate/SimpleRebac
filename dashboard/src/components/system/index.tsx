import React from "react";
import {Card, Col, CountdownProps, message, Progress, Row, Statistic} from "antd";
import {getSystemInfo} from "../../request/api";
import Loading from "../common/Loading";

const { Countdown } = Statistic;

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
      </>;
  }
}

export default App;
