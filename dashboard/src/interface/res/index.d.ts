
interface BaseRes {
    code: string,
    msg: string
}

interface getAllStoreRes extends BaseRes{
    data:Store[]
}

interface Store {
    id: number,
    name: string,
    description: string,
    appKey: string
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
    definition: Definition[]
}
interface Definition {
    description:string
    objectType:string
    permissions:PermissionDefinition[]
    relations:RelationDefinition[]
}
interface PermissionDefinition {
    permission:string
    relationCanAccess:string
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
