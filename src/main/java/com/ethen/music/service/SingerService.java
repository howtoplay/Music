package com.ethen.music.service;

import com.ethen.music.entity.SingerEntity;
import java.util.List;

public interface SingerService {

  List<SingerEntity> getAllSingers();

  int addSinger(SingerEntity singer);

  List<SingerEntity> searchByName(String name);

  SingerEntity searchByNameAll(String name);

  List<SingerEntity> searchBySex(Integer sex);

  int deleteById(Integer id);

  int uploadAvatar(SingerEntity entity);

  int update(SingerEntity entity);
}
