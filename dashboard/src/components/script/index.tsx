import React from 'react';
import { MonacoDiffEditor } from 'react-monaco-editor';

interface PropsType{
  type:string
  onChange:(script:string) => void
}
const App: React.FC<PropsType> = (props:PropsType) => {
  const code1 = `
import xyz.wanghongtao.rebac.engine.script.GroovyScriptBase

class groovyTest extends GroovyScriptBase {

  def fn() {
  //写业务代码
  //$Params 取出参数`;
  const code2 = "  }\n}";
  const options = {
    // renderSideBySide: true
  };

  function onChange(script:string) {
    if (props.type === "json") {
      props.onChange(script)
    } else {
      props.onChange(code1 + script)
    }
  }

  if(props.type === "json") {
    return <MonacoDiffEditor
      width="400"
      height="300"
      language={props.type}
      theme="vs-dark"
      original={""}
      value={"{}"}
      options={options}
      onChange={onChange}
    />
  } else {
    return (
      <MonacoDiffEditor
        width="800"
        height="600"
        language={props.type}
        theme="vs-dark"
        original={code1}
        value={code2}
        options={options}
        onChange={onChange}
      />
    );
  }


}

export default App;
