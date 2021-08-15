package com.ethen.music.controller;

import com.ethen.music.config.Constant;
import com.ethen.music.entity.SingerEntity;
import com.ethen.music.entity.api.ApiResult;
import com.ethen.music.service.SingerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "歌手管理")
@RestController
@RequestMapping("/singer/")
public class SingerController {

  @Autowired
  private SingerService service;

  @ApiOperation(value = "获取所有歌手")
  @ResponseBody
  @GetMapping(value = "all")
  public ApiResult<List<SingerEntity>> getAllSingers() {
    return ApiResult.success(service.getAllSingers());
  }

  @ApiOperation(value = "添加歌手")
  @ResponseBody
  @PostMapping(value = "add")
  public Object addSinger(@RequestBody SingerEntity singer) {
    int res = service.addSinger(singer);
    if (res > 0) {
      return ApiResult.success("添加成功");
    } else if (res == Constant.ERROR_EXIST) {
      return ApiResult.failed("添加失败，歌手已存在");
    } else {
      return ApiResult.failed("添加失败");
    }
  }

  @ApiOperation(value = "根据歌手名查找歌手")
  @ResponseBody
  @GetMapping(value = "search/name")
  public ApiResult<List<SingerEntity>> searchByName(String name) {
    return ApiResult.success(service.searchByName(name));
  }

  @ApiOperation(value = "根据歌手性别查找歌手")
  @ResponseBody
  @GetMapping(value = "search/sex")
  public ApiResult<List<SingerEntity>> searchBySex(Integer sex) {
    return ApiResult.success(service.searchBySex(sex));
  }

  @ApiOperation(value = "删除歌手")
  @ResponseBody
  @DeleteMapping(value = "delete")
  public ApiResult<Object> deleteById(Integer id) {
    int res = service.deleteById(id);
    System.out.println("res = " + res);
    if (res > 0) {
      return ApiResult.success("删除成功");
    } else if (res == Constant.ERROR_NOT_EXIST) {
      return ApiResult.failed("删除失败, 歌手不存在");
    } else {
      return ApiResult.failed("删除失败");
    }
  }

  @ApiOperation(value = "更新歌手信息")
  @ResponseBody
  @PostMapping(value = "update")
  public ApiResult<Object> updateSingerInfo(@RequestBody SingerEntity entity) {
    int res = service.update(entity);
    if (res > 0) {
      return ApiResult.success("修改成功");
    } else {
      return ApiResult.failed("修改失败");
    }
  }

  @ApiOperation(value = "更新歌手头像")
  @ResponseBody
  @PostMapping(value = "update/avatar")
  public ApiResult<?> updateSingerPic(@RequestParam("file") MultipartFile avatar, @RequestParam("id") int id) {

    if (avatar.isEmpty()) {
      return ApiResult.failed("请先选择头像");
    }
    String fileName = System.currentTimeMillis() + avatar.getOriginalFilename();
    File avatarPath = new File(Constant.SINGER_AVATAR_ABSOLUTE);
    if (!avatarPath.exists()) {
      if (!avatarPath.mkdirs()) {
        return ApiResult.failed("上传失败, 地址创建失败");
      }
    }

    File dest = new File(Constant.SINGER_AVATAR_ABSOLUTE + fileName);
    String storeAvatarPath = Constant.SINGER_AVATAR + fileName;
    try {
      avatar.transferTo(dest);
      SingerEntity singer = new SingerEntity();
      singer.setId(id);
      singer.setPic(storeAvatarPath);
      int res = service.uploadAvatar(singer);
      if (res > 0) {
        String url = MvcUriComponentsBuilder.fromMethodName(SingerController.class,
            "getFile",
            fileName
        ).build().toString();
        return ApiResult.success("上传成功", url);
      } else {
        return ApiResult.failed("上传失败");
      }
    } catch (IOException e) {
      return ApiResult.failed("上传失败：" + e.getMessage());
    }
  }

  @ApiIgnore
  @GetMapping("avatar/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable("filename")String fileName){
    File dest = new File(Constant.SINGER_AVATAR_ABSOLUTE + fileName);
    try {
      Resource resource = new UrlResource(dest.toURI());
      if(resource.exists() || resource.isReadable()){
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=\""+resource.getFilename()+"\"")
            .body(resource);
      }else{
        return null;
      }
    } catch (MalformedURLException e) {
      return null;
    }


  }

}
