package com.ethen.music.controller;

import com.ethen.music.config.Constant;
import com.ethen.music.entity.SingerEntity;
import com.ethen.music.entity.SongEntity;
import com.ethen.music.entity.api.ApiResult;
import com.ethen.music.service.SingerService;
import com.ethen.music.service.SongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "歌曲管理")
@RestController
@RequestMapping("/song/")
public class SongController {

  @Autowired
  private SongService service;

  @Autowired
  private SingerService singerService;

  @ApiOperation(value = "获取所有歌曲")
  @ResponseBody
  @GetMapping(value = "all")
  public ApiResult<List<SongEntity>> getAllSongs() {
    return ApiResult.success(service.getAllSongs());
  }

  @ApiOperation(value = "添加歌曲")
  @ResponseBody
  @PostMapping(value = "add")
  public ApiResult<Object> addSinger(@RequestBody SongEntity song) {
    int res = service.addSong(song);
    if (res > 0) {
      return ApiResult.success("添加成功");
    } else if (res == Constant.ERROR_EXIST) {
      return ApiResult.failed("添加失败，歌手已存在");
    } else {
      return ApiResult.failed("添加失败");
    }
  }

  @ApiOperation(value = "根据歌曲名查找歌曲")
  @ResponseBody
  @GetMapping(value = "search/name")
  public ApiResult<List<SongEntity>> searchByName(String name) {
    return ApiResult.success(service.searchByName(name));
  }

  @ApiOperation(value = "根据歌手名查找歌曲")
  @ResponseBody
  @GetMapping(value = "search/singer")
  public ApiResult<List<SongEntity>> searchBySinger(String name) {
    SingerEntity singerEntity = singerService.searchByNameAll(name);
    if (singerEntity == null) {
      return ApiResult.failed("歌手不存在");
    }
    return ApiResult.success(service.searchBySinger(singerEntity.getId()));
  }

  @ApiOperation(value = "删除歌曲")
  @ResponseBody
  @DeleteMapping(value = "delete")
  public ApiResult<Object> deleteById(Integer id) {
    int res = service.deleteById(id);
    if (res > 0) {
      return ApiResult.success("删除成功");
    } else if (res == Constant.ERROR_NOT_EXIST) {
      return ApiResult.failed("删除失败, 歌曲不存在");
    } else {
      return ApiResult.failed("删除失败");
    }
  }

  @ApiOperation(value = "更新歌曲信息")
  @ResponseBody
  @PostMapping(value = "update")
  public ApiResult<Object> updateSongInfo(@RequestBody SongEntity entity) {
    int res = service.update(entity);
    if (res > 0) {
      return ApiResult.success("修改成功");
    } else {
      return ApiResult.failed("修改失败");
    }
  }

  @ApiOperation(value = "更新歌曲封面")
  @ResponseBody
  @PostMapping(value = "update/pic")
  public ApiResult<?> updateSongPic(@RequestParam("file") MultipartFile pic, @RequestParam("id") int id) {

    if (pic.isEmpty()) {
      return ApiResult.failed("请先选择图片");
    }
    String fileName = System.currentTimeMillis() + pic.getOriginalFilename();
    File picPath = new File(Constant.SONG_PIC_ABSOLUTE);
    if (!picPath.exists()) {
      if (!picPath.mkdirs()) {
        return ApiResult.failed("上传失败, 地址创建失败");
      }
    }

    File dest = new File(Constant.SONG_PIC_ABSOLUTE + fileName);
    String storeAvatarPath = Constant.SONG_PIC + fileName;
    try {
      pic.transferTo(dest);
      SongEntity song = new SongEntity();
      song.setId(id);
      song.setPic(storeAvatarPath);
      int res = service.uploadPic(song);
      if (res > 0) {
        return ApiResult.success("上传成功", storeAvatarPath);
      } else {
        return ApiResult.failed("上传失败");
      }
    } catch (IOException e) {
      return ApiResult.failed("上传失败：" + e.getMessage());
    }
  }
}
