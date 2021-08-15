package com.ethen.music.service;

import com.ethen.music.entity.SingerEntity;
import com.ethen.music.entity.SongEntity;
import java.util.List;

public interface SongService {

  List<SongEntity> getAllSongs();

  int addSong(SongEntity song);

  List<SongEntity> searchByName(String name);

  List<SongEntity> searchBySinger(Integer singerId);

  int deleteById(Integer id);

  int uploadPic(SongEntity entity);

  int update(SongEntity entity);
}
