package net.avalon.goodsdemo.core.aop.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.avalon.goodsdemo.util.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * Description:
 *
 * @Author: Weiyin
 * @Create: 2023/2/24 - 20:56
 */
@Aspect
@Component
@Order(10) //值越低，优先级越高
public class ResponseAspect {

    @Value("${goodsdemo.page-size.max}")
    private int max_page_size;

    @Value("${goodsdemo.page-size.default}")
    private int default_page_size;


    @Around("net.avalon.goodsdemo.core.aop.CommonPointCuts.controller()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        ResponseBody ret = null;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        MethodSignature ms = (MethodSignature) pjp.getSignature();
        String[] paramNames = ms.getParameterNames();
        Object[] args = pjp.getArgs();

//        System.out.println(Arrays.toString(args));
        checkPageLimit(request, paramNames, args);
//        System.out.println(Arrays.toString(args));

        try {
            ret = (ResponseBody) pjp.proceed(args);
        } catch (AvalonException e) {
            ret = ResponseUtil.fail(e);
        }

        AvalonStatus code = AvalonStatus.getByCode(ret.getCode());
//        logger.debug("doAround: jp = {}, code = {}", jp.getSignature().getName(), code);
        changeHttpStatus(code, response);

        return ret;

    }

    private void checkPageLimit(HttpServletRequest request, String[] paramNames, Object[] args) {

        Integer page = 1, pageSize = default_page_size;

        String pageString = request.getParameter("page");
        String pageSizeString = request.getParameter("pageSize");

        if (pageString != null && pageString.matches("\\d+")) {
            page = Integer.valueOf(pageString);
            if (page <= 0) {
                page = 1;
            }
        }
        if (pageSizeString != null && pageSizeString.matches("\\d+")) {
            pageSize = Integer.valueOf(pageSizeString);
            if (pageSize <= 0 || pageSize > max_page_size) {
                pageSize = default_page_size;
            }
        }

        for (int i = 0; i < paramNames.length; i++) {
            if ("page".equals(paramNames[i])) {
                args[i] = page;
            }
            if ("pageSize".equals(paramNames[i])) {
                args[i] = pageSize;
            }
        }
    }

    private void changeHttpStatus(AvalonStatus status, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        switch (status) {
            case CREATED:
                //201:
                response.setStatus(201);
//                response.setStatus(HttpServletResponse.SC_CREATED);
                break;

            case RESOURCE_ID_NOTEXIST:
                // 404：资源不存在
                response.setStatus(404);
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;

            case AUTH_INVALID_JWT:
            case AUTH_NEED_LOGIN:
            case AUTH_JWT_EXPIRED:
//            case AUTH_NEED_LOGIN:
                // 401
                response.setStatus(401);
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                break;

            case INTERNAL_SERVER_ERR:
                // 500：数据库或其他严重错误
                response.setStatus(500);
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                break;

            case FIELD_NOTVALID:
            case IMG_FORMAT_ERROR:
            case IMG_SIZE_EXCEED:
            case PARAMETER_MISSED:
                // 400
                response.setStatus(400);
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                break;

            case RESOURCE_ID_OUTSCOPE:
            case FILE_NO_WRITE_PERMISSION:
                // 403
                response.setStatus(403);
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                break;
            default:
                response.setStatus(200);
//                response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
