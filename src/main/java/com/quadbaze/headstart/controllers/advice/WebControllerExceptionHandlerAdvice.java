package com.quadbaze.headstart.controllers.advice;

import com.quadbaze.headstart.controllers.ajax.AjaxResponse;
import com.quadbaze.headstart.controllers.ajax.AjaxResponseException;
import com.quadbaze.headstart.logging.LoggerService;
import com.quadbaze.headstart.utils.ErrorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */

@ControllerAdvice
@ResponseBody
public class WebControllerExceptionHandlerAdvice {

    @Autowired
    private LoggerService LOGGER;

    /**
     * Global Exception Handler for all controllers
     * @param request
     * @param response
     * @param ex
     * @return ${@link ModelAndView} or ${@link AjaxResponse}
     */
    @ExceptionHandler(value = {Exception.class})
    public Object handleExceptions(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String ajaxParam = request.getParameter("ajax");
        if (ex instanceof AjaxResponseException || (StringUtils.isNotBlank(ajaxParam) && ajaxParam.equals(String.valueOf(Boolean.TRUE)))) {

            LOGGER.error("Web-(ajax) Exception", ex);

            return AjaxResponse.swalError(((AjaxResponseException) ex).getTitle(), ex.getMessage());
        } else {

            LOGGER.error("Web (http) Exception", ex);

            Map<String, Object> errorParams = ErrorUtil.getErrorPayload(request, ex);
            ModelAndView mav = new ModelAndView();
            mav.addObject(ErrorUtil.ERROR_PARAMS_KEY, errorParams);
            mav.setViewName(ErrorUtil.ERROR_PAGES_VIEW);
            return mav;
        }
    }
}