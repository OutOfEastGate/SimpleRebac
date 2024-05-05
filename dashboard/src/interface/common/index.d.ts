

interface SystemInfo {
  systemRuntime:string,
  cpuNum:string,
  cpuThreadNum: string,
  totalMemory:string,
  memoryUsage:number,
  jvmMemoryUsage:number,
  permissionInfo:{
    times:number,
    successTimes:number,
    failTimes:number
  },
  logList:any[]
}


type AddRelationType = {
  relation?: string;
  isPenetrate: boolean
};

type AddNodeType = {
  objectType: string;
  objectName: string;
};

type AddDefinitionType = {
  description:string
  objectType:string
}

interface AddModelType {
  storeId:number|undefined
  name:string,
  description:string
}

interface AddStoreType{
  name:string,
  description:string
}

interface FileInfo {
  id:string;
  appId: string;
  url:string;
  filename:string;
  ext:string;
  objectId:string;
  createTime:string;
  attr:object
}
