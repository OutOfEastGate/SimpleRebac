import request from "./index";


export const login = (loginForm:{username:string, password:string}):Promise<LoginRes> => request.post("/user/login", loginForm)
export const register = (registerForm:{username:string, password:string}):Promise<LoginRes> => request.post("/user/register", registerForm)
export const getAppList = ():Promise<getAllAppRes> => request.get("/key/getList")
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

export const checkPermission = (checkPermission:{modelId:string, triple:string}):Promise<checkPermissionRes> => request.post("/checkPermission", checkPermission)

export const checkRelation= (checkRelation:{modelId:string, triple:string}):Promise<checkRelationRes> => request.post("/checkRelation", checkRelation)

export const getFileList = () :Promise<getFileListRes> => request.get("/store/file/list");

export const deleteFile= (deleteFileForm:{objectId:string}) :Promise<BaseRes> => request.post("/store/file/delete", deleteFileForm);
