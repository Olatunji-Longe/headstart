package com.quadbaze.headstart.controllers;

/**
 * @author: Olatunji O. Longe
 * @created: 17 Oct, 2018, 2:07 AM
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */
@Controller
public class HomeController {

    @GetMapping({"/", "/login"})
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        return mav;
    }

}
