import React from 'react';
import { Drawer } from 'antd';
import { Tree } from 'antd';
import ModelDefinition from "./ModelDefinition";
import Loading from "../../../common/Loading";

const { TreeNode } = Tree;
interface PropsType {
  isModalOpen: boolean,
  handleModalClose: () => void,
  handleModelOk: () => void,
  data: Policy | undefined
}

const App: React.FC<PropsType> = (props) => {

  const handleOk = () => {
    props.handleModelOk()
  };

  const handleCancel = () => {
    props.handleModalClose()
  };

  if(props.isModalOpen === undefined) {
    props.isModalOpen = false
  }
  const renderTreeNodes = (data: Definition[] | undefined) =>
  {
    if (data == undefined) {
      return (<Loading></Loading>)
    }

    return <ModelDefinition data={data}></ModelDefinition>
  }

  return (
    <>
      <Drawer title={props.data?.description} open={props.isModalOpen} width={"50%"}  onClose={handleCancel}>
        {renderTreeNodes(props.data?.definitions)}
      </Drawer>
    </>
  );

};

export default App;
