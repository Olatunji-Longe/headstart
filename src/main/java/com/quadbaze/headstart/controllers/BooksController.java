package com.quadbaze.headstart.controllers;

import com.quadbaze.headstart.components.Localizer;
import com.quadbaze.headstart.controllers.ajax.AjaxResponse;
import com.quadbaze.headstart.controllers.ajax.AjaxResponseException;
import com.quadbaze.headstart.controllers.ajax.TargetAction;
import com.quadbaze.headstart.controllers.utils.Alert;
import com.quadbaze.headstart.domain.enums.QueryField;
import com.quadbaze.headstart.domain.models.Book;
import com.quadbaze.headstart.services.BookService;
import com.quadbaze.headstart.services.ajax.AjaxResponseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */
@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AjaxResponseService ajaxResponseService;

    @Autowired
    private Localizer localizer;

    @GetMapping({"", "/search"})
    public ModelAndView books(ModelAndView mav,
            @RequestParam("query") Optional<String> query,
            @RequestParam("queryField") Optional<String> queryField,
            @RequestParam("page") Optional<Integer> page) {

        return searchBooks(mav, query, queryField, page, HttpMethod.GET);
    }

    @PostMapping("/search")
    public ModelAndView search(ModelAndView mav,
            @RequestParam("query") Optional<String> query,
            @RequestParam("queryField") Optional<String> queryField,
            @RequestParam("page") Optional<Integer> page) {

        return searchBooks(mav, query, queryField, page, HttpMethod.POST);
    }

    @PostMapping("/search/ajax")
    public AjaxResponse searchAjax(ModelAndView mav,
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("target") Optional<String> target,
            @RequestParam("targetAction") Optional<String> targetAction,
            @RequestParam("query") Optional<String> query,
            @RequestParam("queryField") Optional<String> queryField,
            @RequestParam("page") Optional<Integer> page) throws Exception {

        mav = searchBooks(mav, query, queryField, page, HttpMethod.POST);
        if(target.isPresent() && targetAction.isPresent()){
            mav.addObject("domTarget", target.get());
            String content = ajaxResponseService.renderViewToString(request, response, mav);
            return AjaxResponse.pageTarget(content, target.get(), TargetAction.valueOf(targetAction.get()));
        }
        throw new AjaxResponseException(HttpStatus.BAD_REQUEST.getReasonPhrase(), localizer.getMessage("ajax.dom.target.unspecified"));
    }

    /**
     * Does the actual books searching by calling the remote endpoint
     * @param mav
     * @param query String to search for
     * @param queryField
     * @param page
     * @param method
     * @return
     */
    private ModelAndView searchBooks(ModelAndView mav, Optional<String> query, Optional<String> queryField, Optional<Integer> page, HttpMethod method){
        boolean isQueryPresent = query.isPresent() && StringUtils.isNotBlank(query.get());
        if(isQueryPresent){
            Page<Book> bookPage = bookService.findBooks(query.get(), QueryField.valueOf(queryField.orElse(QueryField.all.name())), page.orElse(1));
            mav.addObject("pageNums", bookService.getPageNumbers(bookPage));
            mav.addObject("bookPage", bookPage);
            mav.addObject("query", query.get());
            mav.addObject("queryField", queryField.orElse(QueryField.all.name()));
        }else{
            if(method.equals(HttpMethod.POST)){
                mav.addObject(Alert.ATTRIBUTE, Alert.warning(localizer.getMessage("search.query.field.empty")));
            }
        }
        mav.addObject("queryFields", Stream.of(QueryField.values()).map(Enum::name).sorted().collect(Collectors.toList()));
        mav.setViewName("search/index");
        return mav;
    }

}
