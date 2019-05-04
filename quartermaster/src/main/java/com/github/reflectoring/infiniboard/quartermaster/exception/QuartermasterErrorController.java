package com.github.reflectoring.infiniboard.quartermaster.exception;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class QuartermasterErrorController implements ErrorController {

  private ErrorAttributes errorAttributes;

  private static final String ERROR_PATH = "/error";

  public QuartermasterErrorController(ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  @RequestMapping(ERROR_PATH)
  @ResponseBody
  public ResponseEntity<Object> error(HttpServletRequest request) {
    Map<String, Object> attributes = getErrorAttributes(request, getTraceParameter(request));
    HttpStatus status = getStatus(request);

    Object error = attributes.get("error");
    String errorMessage = error != null ? (String) error : null;
    ErrorResource errorResource = new ErrorResource(errorMessage);

    return new ResponseEntity<>(errorResource, status);
  }

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  private boolean getTraceParameter(HttpServletRequest request) {
    String parameter = request.getParameter("trace");

    if ((parameter == null) || "false".equals(parameter.toLowerCase())) {
      return false;
    }

    return true;
  }

  private Map<String, Object> getErrorAttributes(
      HttpServletRequest request, boolean includeStackTrace) {
    RequestAttributes requestAttributes = new ServletRequestAttributes(request);
    return this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    try {
      return HttpStatus.valueOf(statusCode);
    } catch (Exception ex) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }
}
