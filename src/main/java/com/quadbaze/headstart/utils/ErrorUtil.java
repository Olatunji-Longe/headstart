package com.quadbaze.headstart.utils;

import com.quadbaze.headstart.utils.json.Payload;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Olatunji O. Longe on 7/4/2017.
 */
public class ErrorUtil {

    public final static String DEFAULT_ERROR_PATH = "/error";

    public final static String ERROR_PAGES_PATH = "/errors";
    public final static String ERROR_PAGES_VIEW = "errors/index";

    public final static String ERROR_PARAMS_KEY = "errorParams";

    public static final String generateErrorMessage(final HttpStatus status){
        switch(status){
            case NOT_FOUND:  return "The requested resource could not be found!";
            case BAD_REQUEST:  return "Your browser sent an invalid request!";
            case UNAUTHORIZED:  return "Your login credentials could not be authenticated!";
            case FORBIDDEN:  return "You do not have the required permissions to access this resource!";
            case INTERNAL_SERVER_ERROR:  return "Internal Server Error!";
            case SERVICE_UNAVAILABLE:  return "This site is getting a tune-up!";
            default: return "Error Occurred!";
        }
    }

    public static String getExceptionMessage(Exception ex){
        if(ex != null){
            if(ex.getMessage() != null){
                return ex.getMessage();
            }else if(ex.getCause() != null && ex.getCause().getMessage() != null){
                return ex.getCause().getMessage();
            }
            return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }
        return "";
    }

    /**
     * Generates error payload from http request
     * @param request
     * @return
     */
    public static Map<String, Object> getErrorPayload(HttpServletRequest request){
        Object statusCodeObject = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int status = statusCodeObject != null ? (int)statusCodeObject : HttpStatus.INTERNAL_SERVER_ERROR.value();

        String displayMessage = generateErrorMessage(HttpStatus.valueOf(status));

        Object pathObject = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String path = pathObject != null ? String.valueOf(pathObject) : request.getRequestURI();

        return new Payload()
                .set("timestamp", (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a")).format(new Date()))
                .set("status", status)
                .set("error", HttpStatus.valueOf(status).getReasonPhrase())
                .set("message", displayMessage)
                .set("path", path);
    }

    /**
     * Generates error payload from http request and exception
     * @param request
     * @param ex
     * @return
     */
    public static Map<String, Object> getErrorPayload(HttpServletRequest request, Exception ex){
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        String errorTitle = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        String displayMessage = getExceptionMessage(ex);

        if(ex instanceof AccessDeniedException){
            status = HttpStatus.FORBIDDEN.value();
            errorTitle = getExceptionMessage(ex);
            displayMessage = generateErrorMessage(HttpStatus.FORBIDDEN);

        } else if(ex instanceof HttpRequestMethodNotSupportedException){
            status = HttpStatus.METHOD_NOT_ALLOWED.value();
            errorTitle = HttpStatus.BAD_REQUEST.getReasonPhrase();
            displayMessage = generateErrorMessage(HttpStatus.BAD_REQUEST);
        }

        Object pathObject = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String path = pathObject != null ? String.valueOf(pathObject) : request.getRequestURI();

        return new Payload()
                .set("timestamp", (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a")).format(new Date()))
                .set("status", status)
                .set("error", errorTitle)
                .set("message", displayMessage)
                .set("path", path);
    }


    /**
     * Generates error payload from specified errorAttributesMap
     * @param errorAttributesMap
     * @return
     */
    public static Map<String, Object> getErrorPayload(Map<String, Object> errorAttributesMap){
        int status = errorAttributesMap.get("status") != null ? (int)errorAttributesMap.get("status") : HttpStatus.INTERNAL_SERVER_ERROR.value();

        String displayMessage = generateErrorMessage(HttpStatus.valueOf(status));

        Object dateObject = errorAttributesMap.get("timestamp");
        Date date = dateObject != null ? (Date)dateObject : new Date();
        String timestamp = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a")).format(date);

        return new Payload()
                .set("timestamp", timestamp)
                .set("status", status)
                .set("error", errorAttributesMap.get("error"))
                .set("message", displayMessage)
                .set("path", errorAttributesMap.get("path"));
    }
}
