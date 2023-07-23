import React from 'react';
import {Select, Tooltip} from 'antd';

interface propsType {
    ops:selector[]
    handleOpsChange: (id:number)=>void
}
interface selector{
    value:string
    label: string
    desc: string
}
function App(props:propsType){

    const handleChange = (value: number) => {
        props.handleOpsChange(value)
    };
    return(
        <Select
            showSearch
            style={{ width: 200 }}
            placeholder="选择存储模型"
            optionFilterProp="children"
            onChange={handleChange}
            filterOption={(input, option) => (option?.label ?? '').includes(input)}
            filterSort={(optionA, optionB) =>
                (optionA?.label ?? '').toLowerCase().localeCompare((optionB?.label ?? '').toLowerCase())
            }
            options={props.ops}
        />
    )
}




export default App;