package com.quadbaze.headstart.services.ajax;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Olatunji O. Longe on 7/26/17.
 */
public interface AjaxResponseService {
    String renderViewToString(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) throws Exception;
}
