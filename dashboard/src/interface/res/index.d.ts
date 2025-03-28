
interface BaseRes {
    code: string,
    msg: string
}
interface LoginRes extends BaseRes {
  data: {
    token:string
  }
}
interface RegisterRes extends BaseRes {
  data: {
    username:string
    token:string
  }
}
interface getAllStoreRes extends BaseRes{
    data:Store[]
}

interface getAllAppRes extends BaseRes {
  data:AppKey[]
}

interface Store {
    id: number,
    name: string,
    description: string,
    appKey: string
}

interface AppKey {
  id:string,
  appName:string,
  appIcon:string,
  appKey:string,
  secretKey:string
}

interface getAllModelRes extends BaseRes {
    data: Model[]
}
interface Model {
    id: string,
    storeId: number,
    name: string,
    description: string,
    policyId: string
}

interface getPolicyRes extends BaseRes {
    data: Policy
}

interface Policy {
    id: string
    description:string
    definitions: Definition[]
}
interface Definition {
    policyId:string,
    key: string,
    description:string
    objectType:string
    permissions:PermissionDefinition[]
    relations:RelationDefinition[]
}
interface PermissionDefinition {
    permission:string
    relationCanAccess:string
    script:string|undefined
}

interface RelationDefinition {
    relation:string
    subjectType:string
}

interface getRelationRes extends BaseRes {
    data:Relation[]
}

interface Relation {
    id: number,
    modelId: number,
    objectType: string,
    object: string,
    relation: string,
    subjectType: string,
    subject: string
    triple: string
}

interface getGraphRes extends BaseRes{
    data: Graph
}

interface getSystemInfoRes extends BaseRes{
  data: any
}

interface getGraphPreviewRes extends BaseRes{
  data: any
}

interface Graph {
    nodes:GraphNode[]
    links:Link[]
}

interface Link {
    source: GraphNode;
    target: GraphNode;
    text: string;
}

interface GraphNode extends d3.SimulationNodeDatum {
    id: string;
}

interface checkPermissionRes extends BaseRes{
  data:{
    hasPermission: Boolean,
    useTime: number
  }
}
interface checkRelationRes extends BaseRes{
  data:Boolean
}

interface getFileListRes extends BaseRes{
  data:FileInfo[]
}

interface uploadFileRes extends BaseRes{
  data:string
}

interface executeScriptRes extends BaseRes{
  data:any
}
