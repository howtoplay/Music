package com.ethen.music.service.impl;

import com.ethen.music.config.Constant;
import com.ethen.music.entity.SingerEntity;
import com.ethen.music.entity.SongEntity;
import com.ethen.music.mapper.SongMapper;
import com.ethen.music.service.SongService;
import com.ethen.music.wrapper.SingerUpdate;
import com.ethen.music.wrapper.SongUpdate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl implements SongService {

  @Qualifier("songMapper")
  @Autowired
  private SongMapper mapper;

  @Override
  public List<SongEntity> getAllSongs() {
    return mapper.listEntity(mapper.defaultQuery());
  }

  @Override
  public int addSong(SongEntity song) {
    SongEntity entity = mapper.findOne(mapper.query().where
        .singerId().eq(song.getSingerId()).and
        .name().eq(song.getName())
        .end());
    if (entity != null) {
      return Constant.ERROR_EXIST;
    }
    return mapper.insert(song);
  }

  @Override
  public List<SongEntity> searchByName(String name) {
    return mapper.listEntity(mapper.query().where.name().like("%" + name + "%").end());
  }

  @Override
  public List<SongEntity> searchBySinger(Integer singerId) {
    return mapper.listEntity(mapper.query().where.singerId().eq(singerId).end());
  }

  @Override
  public int deleteById(Integer id) {
    SongEntity entity = mapper.findById(id);
    if (entity == null) {
      return Constant.ERROR_NOT_EXIST;
    }
    return mapper.deleteById(id);
  }

  @Override
  public int uploadPic(SongEntity entity) {
    return mapper.updateBy(new SongUpdate().set.pic().is(entity.getPic()).end().where.id().eq(entity.getId()).end());
  }

  @Override
  public int update(SongEntity entity) {
    return mapper.updateById(entity);
  }
}
