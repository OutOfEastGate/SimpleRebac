import request from "./index";

export const getAllStore = ():Promise<getAllStoreRes> => request.get("/store/getAllByAppKey");

export const getAllModel = (id:number):Promise<getAllModelRes> => request.get("/model/getAllByStoreId?storeId=" + id);

export const getPolicy = (id:string | number):Promise<getPolicyRes> => request.get("/model/getPolicyById?id=" + id)

export const updatePolicy = (updatePolicyForm: Policy):Promise<BaseRes> => request.post("/model/updatePolicy", updatePolicyForm)

export const getRelation = (id:string):Promise<getRelationRes> => request.get("/relation/getByModelId?modelId=" + id)

export const getGraph = (id:string):Promise<getGraphRes> => request.get("relation/getGraphRelation?modelId=" + id)

export const getSystemInfo = ():Promise<getSystemInfoRes> => request.get("system/getSystemInfo")

export const getGraphPreview = (id:string):Promise<getGraphPreviewRes> => request.get("graph/get/" + id)

export const saveGraphPreview = (graphDo:any):Promise<getGraphPreviewRes> => request.post("graph/save", graphDo)
