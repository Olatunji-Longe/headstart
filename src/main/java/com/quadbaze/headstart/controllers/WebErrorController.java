package com.quadbaze.headstart.controllers;

import com.quadbaze.headstart.controllers.ajax.AjaxResponseException;
import com.quadbaze.headstart.logging.LoggerService;
import com.quadbaze.headstart.utils.ErrorUtil;
import com.quadbaze.headstart.utils.json.GsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Olatunji on 6/11/2016.
 */

@Controller
@RequestMapping(ErrorUtil.DEFAULT_ERROR_PATH)
public class WebErrorController extends AbstractErrorController {

    @Autowired
    private LoggerService LOGGER;

    public WebErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @GetMapping
    public Object renderErrorPage(final HttpServletRequest request, final HttpServletResponse response, ModelAndView mav) {
        Map<String, Object> errorAttributesMap = super.getErrorAttributes(request, false);

        boolean doLogging = true;
        if(errorAttributesMap.containsKey("path") && errorAttributesMap.get("path") != null){
            String path = (String)errorAttributesMap.get("path");
            doLogging = !path.endsWith(".js") && !path.endsWith(".css") && !path.endsWith(".map");
        }

        if(doLogging){
            LOGGER.error(String.valueOf(GsonUtil.toString(errorAttributesMap)));
        }

        Map<String, Object> errorParams = ErrorUtil.getErrorPayload(errorAttributesMap);

        String ajaxParam = request.getParameter("ajax");
        if(StringUtils.isNotBlank(ajaxParam) && ajaxParam.equals(String.valueOf(Boolean.TRUE))){
            throw new AjaxResponseException(String.valueOf(errorParams.get("error")), String.valueOf(errorParams.get("message")));
        }else{
            mav.addObject(ErrorUtil.ERROR_PARAMS_KEY, errorParams);
            mav.setViewName(ErrorUtil.ERROR_PAGES_VIEW);
            return mav;
        }
    }

    @Override
    public String getErrorPath() {
        return ErrorUtil.DEFAULT_ERROR_PATH;
    }
}