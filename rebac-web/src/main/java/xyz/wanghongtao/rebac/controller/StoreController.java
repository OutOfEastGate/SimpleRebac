package xyz.wanghongtao.rebac.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.wanghongtao.rebac.object.dataObject.FileDo;
import xyz.wanghongtao.rebac.object.dataObject.StoreDo;
import xyz.wanghongtao.rebac.object.form.store.AddStoreForm;
import xyz.wanghongtao.rebac.object.form.store.DeleteFileForm;
import xyz.wanghongtao.rebac.object.form.store.UploadFileForm;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.object.viewObject.store.AddStore;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;
import xyz.wanghongtao.rebac.service.gateway.FileDatabaseGateway;

import java.io.IOException;
import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/16 18:11
 */
@AllArgsConstructor
@RequestMapping("/store")
@RestController
public class StoreController {

    DatabaseGateway databaseGateway;

    FileDatabaseGateway fileDatabaseGateway;

    @PostMapping("/add")
    public Result<AddStore> add(@RequestBody @Valid AddStoreForm addStoreForm, @RequestHeader("appKey") String appKey) {
        addStoreForm.setAppKey(appKey);
        return Result.success(databaseGateway.addStore(addStoreForm));
    }

    @GetMapping("/getAllByAppKey")
    public Result<List<StoreDo>> getAllByAppId(@RequestHeader("appKey") String appKey) {
        return Result.success(databaseGateway.getAllByAppId(appKey));
    }

    @GetMapping("/getStoreById")
    public Result<StoreDo> getStoreById(Long id) {
        return Result.success(databaseGateway.getStoreById(id));
    }


  @PostMapping("/uploadFile")
  public Result<String> upload(MultipartFile file, @RequestHeader("appKey") String appKey, @RequestHeader("domain") String domain) {
    UploadFileForm uploadFileForm = new UploadFileForm();
    uploadFileForm.setAppKey(appKey);
    uploadFileForm.setDomain(domain);
    return Result.success(fileDatabaseGateway.uploadFile(file.getResource(), uploadFileForm));
  }

  @GetMapping("/getFile")
  public Result<?> getFile(String filename, HttpServletResponse response) {
    try {
      fileDatabaseGateway.getFile(filename, response.getOutputStream());
      return Result.success();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/image/{filename}")
  public Result<?> getImage(@PathVariable("filename") String filename, HttpServletResponse response) {
    try {
      fileDatabaseGateway.getFile(filename, response.getOutputStream());
      return Result.success();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/file/list")
  public Result<List<FileDo>> getFileList() {
    return Result.success(fileDatabaseGateway.getFileList());
  }

  @PostMapping("/file/delete")
  public Result<?> deleteFile(@RequestBody @Valid DeleteFileForm deleteFileForm) {
    fileDatabaseGateway.deleteFile(deleteFileForm);
    return Result.success();
  }

}
