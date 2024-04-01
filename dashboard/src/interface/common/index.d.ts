

interface SystemInfo {
  systemRuntime:string,
  cpuNum:string,
  cpuThreadNum: string,
  totalMemory:string,
  memoryUsage:number,
  jvmMemoryUsage:number
}


type AddRelationType = {
  relation?: string;
  isPenetrate: boolean
};

type AddNodeType = {
  objectType: string;
  objectName: string;
};
