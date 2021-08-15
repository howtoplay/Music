package com.ethen.music.controller;

import com.ethen.music.config.jwt.JwtTokenUtil;
import com.ethen.music.entity.api.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户管理")
@RequestMapping("/user/")
@RestController
public class UserController {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @ApiOperation(value = "登录")
  @ResponseBody
  @PostMapping("login")
  public ApiResult<String> login(String name, String password) {

    String token;
    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(name);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        return ApiResult.failed("密码错误");
      }
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(userDetails);
    } catch (AuthenticationException e) {
      return ApiResult.failed(e.getMessage());
    }
    return ApiResult.success("登陆成功", tokenHead + token);
  }
}
