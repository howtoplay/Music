package com.ethen.music.entity.api;

/**
 * 通用返回对象
 */
public class ApiResult<T> {

  private long code;
  private String msg;
  private T data;

  protected ApiResult(long code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  /**
   * 成功返回结果
   *
   * @param data 获取的数据
   */
  public static <T> ApiResult<T> success(T data) {
    return new ApiResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
  }

  /**
   * 成功返回结果
   *
   * @param msg 提示信息
   */
  public static <T> ApiResult<T> success(String msg) {
    return new ApiResult<T>(ResultCode.SUCCESS.getCode(), msg, null);
  }

  /**
   * 成功返回结果
   *
   * @param data    获取的数据
   * @param message 提示信息
   */
  public static <T> ApiResult<T> success(String message, T data) {
    return new ApiResult<T>(ResultCode.SUCCESS.getCode(), message, data);
  }

  /**
   * 失败返回结果
   *
   * @param errorCode 错误码
   */
  public static <T> ApiResult<T> failed(IErrorCode errorCode) {
    return new ApiResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
  }

  /**
   * 失败返回结果
   *
   * @param message 提示信息
   */
  public static <T> ApiResult<T> failed(String message) {
    return new ApiResult<T>(ResultCode.FAILED.getCode(), message, null);
  }

  /**
   * 失败返回结果
   */
  public static <T> ApiResult<T> failed() {
    return failed(ResultCode.FAILED);
  }

  /**
   * 参数验证失败返回结果
   */
  public static <T> ApiResult<T> validateFailed() {
    return failed(ResultCode.VALIDATE_FAILED);
  }

  /**
   * 参数验证失败返回结果
   *
   * @param message 提示信息
   */
  public static <T> ApiResult<T> validateFailed(String message) {
    return new ApiResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
  }

  /**
   * 未登录返回结果
   */
  public static <T> ApiResult<T> unauthorized(T data) {
    return new ApiResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
  }

  /**
   * 未授权返回结果
   */
  public static <T> ApiResult<T> forbidden(T data) {
    return new ApiResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
  }

  public long getCode() {
    return code;
  }

  public void setCode(long code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
