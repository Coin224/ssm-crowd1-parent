package com.lch.ssm.mvc.exception;

import com.google.gson.Gson;
import com.lch.ssm.util.ReqTypeUtil;
import com.lch.ssm.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lch
 * @create 2020-07-17 14:53
 * 异常处理类
 */


@ControllerAdvice//表示当前类是一个异常处理类
public class ExceptionResolver {

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView resolveNullPoint(NullPointerException exception,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ModelAndView resolveNullPoint(ArithmeticException exception,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }


    private static ModelAndView commonResolve(String viewName,
                                       Exception exception,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        // 1.判断请求的类型
        boolean reqType = ReqTypeUtil.judgeReqType(request);
        // 2.ajax请求异常处理
        if (reqType) {
            // 2.1 获取异常的消息
            String message = exception.getMessage();
            // 2.2 传入resultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.faildWithMessage(message);
            // 2.3 把resultEntity转换成json字符串
            Gson gson = new Gson();
            String result = gson.toJson(resultEntity);
            // 2.4 获取打印流把json字符串打印到页面
            PrintWriter writer = response.getWriter();
            writer.print(result);
            return null;
        }
        // 3.普通请求
        ModelAndView modelAndView = new ModelAndView();
        // 3.1 将exception存入modelandview
        modelAndView.addObject("exception",exception);
        // 3.2 跳转到对应页面
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
