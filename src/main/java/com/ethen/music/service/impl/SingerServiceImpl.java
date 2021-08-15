package com.ethen.music.service.impl;

import com.ethen.music.config.Constant;
import com.ethen.music.entity.SingerEntity;
import com.ethen.music.mapper.SingerMapper;
import com.ethen.music.service.SingerService;
import com.ethen.music.wrapper.SingerUpdate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SingerServiceImpl implements SingerService {

  @Qualifier("singerMapper")
  @Autowired
  private SingerMapper mapper;

  @Override
  public List<SingerEntity> getAllSingers() {
    return mapper.listEntity(mapper.defaultQuery());
  }

  @Override
  public int addSinger(SingerEntity singer) {
    SingerEntity entity = mapper.findOne(mapper.query().where
        .name().eq(singer.getName()).and
        .sex().eq(singer.getSex()).and
        .location().eq(singer.getLocation()).and
        .birth().eq(singer.getBirth())
        .end());
    if (entity != null) {
      return Constant.ERROR_EXIST;
    }
    return mapper.insert(singer);
  }

  @Override
  public List<SingerEntity> searchByName(String name) {
    return mapper.listEntity(mapper.query().where.name().like("%" + name + "%").end());
  }

  @Override
  public SingerEntity searchByNameAll(String name) {
    return mapper.findOne(mapper.query().where.name().eq(name).end());
  }

  @Override
  public List<SingerEntity> searchBySex(Integer sex) {
    return mapper.listEntity(mapper.query().where().sex().eq(sex).end());
  }

  @Override
  public int deleteById(Integer id) {
    SingerEntity entity = mapper.findById(id);
    if (entity == null) {
      return Constant.ERROR_NOT_EXIST;
    }
    return mapper.deleteById(id);
  }

  @Override
  public int uploadAvatar(SingerEntity entity) {
    return mapper.updateBy(new SingerUpdate().set.pic().is(entity.getPic()).end().where.id().eq(entity.getId()).end());
  }

  @Override
  public int update(SingerEntity entity) {
    return mapper.updateById(entity);
  }
}
