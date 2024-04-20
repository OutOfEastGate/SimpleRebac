import React, {useEffect, useState} from 'react';
import { Drawer } from 'antd';
import { Tree } from 'antd';
import ModelDefinition from "./ModelDefinition";
import Loading from "../../../common/Loading";

const { TreeNode } = Tree;
interface PropsType {
  isModalOpen: boolean,
  handleModalClose: () => void,
  handleModelOk: () => void,
  updatePolicy: (definition: Definition) => void,
  addDefinition: (definition: Definition) => void,
  deleteDefinition: (definition: Definition) => void,
  data: Policy | undefined
}

const App: React.FC<PropsType> = (props) => {

  const [data, setData] = useState(props.data);

  useEffect(() => {
    setData(props.data);
  }, [props.data]);

  const handleOk = () => {
    props.handleModelOk()
  };

  const handleCancel = () => {
    props.handleModalClose()
  };

  const updateDefinition = (definition:Definition) => {
    props.updatePolicy(definition)
  }

  if(props.isModalOpen === undefined) {
    props.isModalOpen = false
  }

  function addDefinition(def: Definition) {
    props.addDefinition(def)
  }

  function deleteDefinition(def: Definition) {
    props.deleteDefinition(def)
  }

  const renderTreeNodes = (policy: Policy | undefined) =>
  {
    let data = policy?.definitions
    if (policy === undefined || data === undefined || policy.id === undefined) {
      return (<Loading></Loading>)
    }
    data.forEach(d => {
      d.policyId = policy.id
    })

    return <ModelDefinition policy={policy}
                            data={data}
                            updateDefinition={updateDefinition}
                            addDefinition={addDefinition}
                            deleteDefinition={deleteDefinition}
    ></ModelDefinition>
  }

  return (
    <>
      <Drawer title={props.data?.description} open={props.isModalOpen} width={"50%"}  onClose={handleCancel}>
        {renderTreeNodes(data)}
      </Drawer>
    </>
  );

};

export default App;
