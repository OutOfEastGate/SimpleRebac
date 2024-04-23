import request from "./index";
import {add} from "husky";

export const saveStore = (addStore:AddStoreType):Promise<BaseRes> => request.post("/store/add", addStore)
export const getAllStore = ():Promise<getAllStoreRes> => request.get("/store/getAllByAppKey");

export const getAllModel = (id:number):Promise<getAllModelRes> => request.get("/model/getAllByStoreId?storeId=" + id);

export const saveModel = (model:AddModelType):Promise<BaseRes> => request.post("/model/add", model)

export const deleteModelById = (id:string):Promise<BaseRes> => request.post("/model/delete", {id:id})

export const getPolicy = (id:string | number):Promise<getPolicyRes> => request.get("/model/getPolicyById?id=" + id)

export const updatePolicy = (updatePolicyForm: Policy):Promise<BaseRes> => request.post("/model/updatePolicy", updatePolicyForm)

export const getRelation = (id:string):Promise<getRelationRes> => request.get("/relation/getByModelId?modelId=" + id)

export const getGraph = (id:string):Promise<getGraphRes> => request.get("relation/getGraphRelation?modelId=" + id)

export const getSystemInfo = ():Promise<getSystemInfoRes> => request.get("system/getSystemInfo")

export const getGraphPreview = (id:string):Promise<getGraphPreviewRes> => request.get("graph/get/" + id)

export const saveGraphPreview = (graphDo:any):Promise<getGraphPreviewRes> => request.post("graph/save", graphDo)

export const checkPermission = (checkPermission:CheckPermissionParam):Promise<checkPermissionRes> => request.post("/checkPermission", checkPermission)
