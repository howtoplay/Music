package com.ethen.music.service;

import com.ethen.music.entity.AdminEntity;

public interface AdminService {

  AdminEntity login(String name, String password);

  AdminEntity getAdminByName(String name);
}
