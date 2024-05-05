package xyz.wanghongtao.rebac.service.gateway;

import org.springframework.core.io.Resource;
import xyz.wanghongtao.rebac.object.dataObject.FileDo;
import xyz.wanghongtao.rebac.object.form.store.DeleteFileForm;
import xyz.wanghongtao.rebac.object.form.store.UploadFileForm;

import java.io.OutputStream;
import java.util.List;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
public interface FileDatabaseGateway {

  String uploadFile(Resource resource, UploadFileForm uploadFileForm);

  void getFile(String filename, OutputStream outputStream);

  List<FileDo> getFileList();

  void deleteFile(DeleteFileForm deleteFileForm);
}
