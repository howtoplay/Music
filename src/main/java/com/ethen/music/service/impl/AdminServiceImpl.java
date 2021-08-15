package com.ethen.music.service.impl;

import com.ethen.music.entity.AdminEntity;
import com.ethen.music.entity.api.ApiResult;
import com.ethen.music.mapper.AdminMapper;
import com.ethen.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  @Qualifier("adminMapper")
  @Autowired
  private AdminMapper mapper;

  @Override
  public AdminEntity login(String name, String password) {
    return mapper.findOne(mapper.query().where().name().eq(name).and.password().eq(password).end());
  }

  @Override
  public AdminEntity getAdminByName(String name) {
    return mapper.findOne(mapper.query().where.name().eq(name).end());
  }
}
