package xyz.wanghongtao.rebac.gateway;

import com.mongodb.client.gridfs.GridFSFindIterable;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.dataObject.FileDo;
import xyz.wanghongtao.rebac.object.form.store.DeleteFileForm;
import xyz.wanghongtao.rebac.object.form.store.UploadFileForm;
import xyz.wanghongtao.rebac.repository.FileRepository;
import xyz.wanghongtao.rebac.service.gateway.FileDatabaseGateway;

import java.io.*;
import java.util.List;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
@AllArgsConstructor
@Component
public class FileDatabaseGatewayImpl implements FileDatabaseGateway {
  private FileRepository fileRepository;
  private GridFsTemplate gridFsTemplate;


  @Override
  public String uploadFile(Resource resource, UploadFileForm uploadFileForm) {
    try {
      InputStream inputStream = resource.getInputStream();
      String filename = resource.getFilename();
      assert filename != null;
      String ext = "";
      int dotIndex = filename.lastIndexOf(".");
      if (dotIndex > 0 && dotIndex < filename.length() - 1) {
        ext = filename.substring(dotIndex + 1);
      }

      ObjectId objectId = gridFsTemplate.store(inputStream, filename);
      fileRepository.save(FileDo.builder()
          .appKey(uploadFileForm.getAppKey())
          .ext(ext)
          .filename(filename)
          .objectId(objectId.toString())
          .length(resource.contentLength())
          .url("http://" + uploadFileForm.getDomain() + "/store/image/" + filename)
          .createTime(System.currentTimeMillis())
          .build());
      return objectId.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void getFile(String resource, OutputStream outputStream) {
    GridFsResource gridFsResource = gridFsTemplate.getResource(resource);
    try {
      gridFsResource.getInputStream().transferTo(outputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<FileDo> getFileList() {
    return fileRepository.findAll();
  }

  @Override
  public void deleteFile(DeleteFileForm deleteFileForm) {
    List<FileDo> fileDoList = fileRepository.findByObjectId(deleteFileForm.getObjectId());
    fileRepository.deleteAll(fileDoList);
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(deleteFileForm.getObjectId()));
    gridFsTemplate.delete(query);
  }
}
