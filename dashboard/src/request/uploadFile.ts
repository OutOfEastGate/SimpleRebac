import request from "./index";
import {backPath} from "../components/conf";

export const  uploadFile = (file: File):Promise<uploadFileRes> => {
  const formData = new FormData();
  formData.append('file', file);
  return request.post('http://' + backPath + '/store/uploadFile', formData, {
    headers: {
      token: localStorage.getItem("token") as string,
      appKey: localStorage.getItem("appKey") as string,
      domain: window.location.host,
    }
  })
}
