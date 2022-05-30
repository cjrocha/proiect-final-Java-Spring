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

/**
 * Taking carer of exceptions
 * redirecting to error page
 */
@Controller("/error")
public class ErrorController implements AccessDeniedHandler {
    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    /**
     * Serves error page for any exceptions raised
     * @param request - request received
     * @param httpServletResponse - response from server
     * @param accessDeniedException - denial of access
     * @param exception - any other exceptions that may be raised
     * @return - error page and logs event
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handle
            (HttpServletRequest request, HttpServletResponse httpServletResponse,
             AccessDeniedException accessDeniedException, Exception exception){
        ModelAndView mv = new ModelAndView();

        mv.addObject("exception", exception.getLocalizedMessage());
        mv.addObject("url", request.getRequestURL());
        mv.addObject("reponse", httpServletResponse.getStatus());
        logger.info("Am inregistrat o eroare: "+exception.getLocalizedMessage()+
                ". In momentul in care s-a accesat: "+request.getRequestURL()+
                " am primit raspuns: "+httpServletResponse.getStatus());
        mv.setViewName("/error");
        return mv;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.info("hmm ..... avem o eroare!");
    }
}
