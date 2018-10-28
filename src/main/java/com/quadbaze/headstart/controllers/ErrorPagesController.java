package com.quadbaze.headstart.controllers;

import com.quadbaze.headstart.utils.ErrorUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: Olatunji O. Longe
 * @created: 17 Oct, 2018, 8:42 PM
 */
@Controller
@RequestMapping(ErrorUtil.ERROR_PAGES_PATH)
public class ErrorPagesController {

    @GetMapping
    public ModelAndView root(ModelAndView mav, HttpServletRequest request) {
        Map<String, Object> errorParams = ErrorUtil.getErrorPayload(request);
        mav.addObject(ErrorUtil.ERROR_PARAMS_KEY, errorParams);
        mav.setViewName(ErrorUtil.ERROR_PAGES_VIEW);
        return mav;
    }

}
