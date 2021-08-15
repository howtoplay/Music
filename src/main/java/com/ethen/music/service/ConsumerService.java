package com.ethen.music.service;

import com.ethen.music.entity.AdminEntity;
import com.ethen.music.entity.ConsumerEntity;

public interface ConsumerService {

  ConsumerEntity getConsumerByName(String name);

}
