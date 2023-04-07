package com.shanzhu.platform.security;


import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.shanzhu.platform.entity.dto.UserIdReqDTO;
import com.shanzhu.platform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 山竹
 * @version 1.0
 * @description: TODO
 * @date 2023/3/11 9:47
 */
@Configuration
@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer, StpInterface {

    @Autowired
    private UserService userService;

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaRouteInterceptor((request, response, handler) -> {
            //登录认证
            SaRouter.match("/auth/login", () -> StpUtil.checkLogin());
            SaRouter.match("/user/**", () -> StpUtil.checkRoleOr("admin"));
            SaRouter.match("/news/**", () -> StpUtil.checkRoleOr("admin","user"));
            SaRouter.match("/content/**", () -> StpUtil.checkRoleOr("admin","user"));
            SaRouter.match("/news-column/**", () -> StpUtil.checkRoleOr("admin","user"));
            SaRouter.match("/tag/**", () -> StpUtil.checkRoleOr("admin","user"));
            SaRouter.match("/oss/**", () -> StpUtil.checkRoleOr("admin","user"));
            SaRouter.match("/subscribe/**", () -> StpUtil.checkRoleOr("admin","user"));
            SaRouter.match("/utils/**", () -> StpUtil.checkRoleOr("admin","user"));
        })).addPathPatterns("/**").excludePathPatterns(
                "/swagger**/**","/webjars/**","/v3/**","/doc.html","/auth/**","msm/**");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/springfox-swagger-ui/"
        );
    }


    /*
      不对资源划分等级
    */
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    /*重写权限*/
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        log.info("loginId:" + loginId + "||" + loginType);
        UserIdReqDTO idReqDTO = new UserIdReqDTO();
        idReqDTO.setId(loginId.toString());
        List<String> role = userService.queryUserIdByRole(idReqDTO);
        return role;
    }


}
