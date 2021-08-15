package com.ethen.music.service.impl;

import com.ethen.music.entity.ConsumerEntity;
import com.ethen.music.mapper.AdminMapper;
import com.ethen.music.mapper.ConsumerMapper;
import com.ethen.music.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

  @Qualifier("consumerMapper")
  @Autowired
  private ConsumerMapper mapper;

  @Override
  public ConsumerEntity getConsumerByName(String name) {
    return mapper.findOne(mapper.query().where.username().eq(name).end());
  }
}
