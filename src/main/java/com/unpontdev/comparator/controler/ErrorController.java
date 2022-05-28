package com.unpontdev.comparator.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Controller("/error")
public class ErrorController implements AccessDeniedHandler {
    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handle
            (HttpServletRequest request, HttpServletResponse httpServletResponse,
             AccessDeniedException e, Exception ex){
        ModelAndView mv = new ModelAndView();

        mv.addObject("exception", ex.getLocalizedMessage());
        mv.addObject("url", request.getRequestURL());
        mv.addObject("reponse", httpServletResponse.getStatus());
        logger.info("hmm ..... avem o eroare!");
        mv.setViewName("/error");
        return mv;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.info("hmm ..... avem o eroare!");
    }
}
