package com.ethen.music.config;

public class Constant {

  public static final String ROOT = "/home/ethen/Documents/data";

  public static final String SINGER_AVATAR = "/img/singerPic/";
  public static final String SINGER_AVATAR_ABSOLUTE = ROOT + SINGER_AVATAR;

  public static final String SONG_PIC = "/img/songPic/";
  public static final String SONG_PIC_ABSOLUTE = ROOT + SONG_PIC;

  public static final int ERROR_EXIST = -1; // 已存在，无法插入数据库错误
  public static final int ERROR_NOT_EXIST = -2; // 不存在，无法删除数据库错误

}
