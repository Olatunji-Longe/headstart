package com.quadbaze.headstart.services.ajax;

import com.quadbaze.headstart.controllers.ajax.AjaxResponseException;
import com.quadbaze.headstart.controllers.utils.CharArrayWriterResponse;
import com.quadbaze.headstart.logging.LoggerService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Olatunji O. Longe on 9/17/2016.
 */
@Service
public class AjaxResponseServiceImpl implements AjaxResponseService {

    @Autowired
    private LoggerService LOGGER;

    private ViewResolver viewResolver;

    @Autowired
    public AjaxResponseServiceImpl (ViewResolver viewResolver){
        this.viewResolver = viewResolver;
    }

    /**
     * Extracts content of ModelAndView as html string
     * @param request
     * @param response
     * @param mav
     * @return html String representation of the view
     * @throws AjaxResponseException
     */
    @Override
    public String renderViewToString(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) throws AjaxResponseException {
        try{
            View view = viewResolver.resolveViewName(mav.getViewName(), Locale.getDefault());
            CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
            view.render(mav.getModelMap(), request, customResponse);
            Document document = Jsoup.parse(customResponse.getOutput());
            return getContent(document, mav.getModel());
        }catch(Exception ex){
            String message = "Error while rendering response";
            LOGGER.error(String.format("%s for View: %s | Model: %s ", message, mav.getViewName(), mav.getModel()));
            throw new AjaxResponseException(ex, "View Rendering Error!", message);
        }
    }

    /**
     * Evaluates the content to be returned to the view
     * @param document
     * @param model
     * @return html String
     */
    private String getContent(Document document, Map<String, Object> model) {
        if(model.containsKey("domTarget")){
            String domTarget = (String)model.get("domTarget");
            if(domTarget.startsWith("#")){
                return document.getElementById(StringUtils.removeStart(domTarget, "#")).outerHtml();
            } else if(domTarget.startsWith(".")){
                return document.getElementsByClass(StringUtils.removeStart(domTarget, ".")).outerHtml();
            } else {
                Elements elements = document.getElementsByAttribute("data-target-id");
                return elements.size() == 1 ? elements.get(0).outerHtml() : document.outerHtml();
            }
        }else{
            Elements elements = document.getElementsByTag("html");
            return elements.size() == 1 ? elements.get(0).outerHtml() : document.outerHtml();
        }
    }
}
