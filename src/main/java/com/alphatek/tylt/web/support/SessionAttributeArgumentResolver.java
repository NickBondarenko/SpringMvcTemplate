package com.alphatek.tylt.web.support;

import org.springframework.core.MethodParameter;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;

public class SessionAttributeArgumentResolver implements HandlerMethodArgumentResolver {

	@Override	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,	WebDataBinderFactory binderFactory) throws Exception {
    Annotation[] parameterAnnotations = parameter.getParameterAnnotations();
    Class<?> parameterType = parameter.getParameterType();

    for (Annotation parameterAnnotation : parameterAnnotations) {
      if (SessionAttribute.class.isInstance(parameterAnnotation)) {
        SessionAttribute sessionParam = (SessionAttribute) parameterAnnotation;
        String parameterName = sessionParam.value();
        boolean required = sessionParam.required();
        String defaultValue = sessionParam.defaultValue();

        HttpServletRequest httpRequest = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = httpRequest.getSession(false);

        Object result = null;
        if (session != null) { result = session.getAttribute(parameterName); }
        if (result == null) { result = defaultValue; }
        if (result == null && required && session == null) { raiseSessionRequiredException(parameterName, parameterType); }
        if (result == null && required) { raiseMissingParameterException(parameterName, parameterType); }

        return result;
      }
    }
    return WebArgumentResolver.UNRESOLVED;
	}

  protected void raiseMissingParameterException(String parameterName, Class<?> parameterType) {
    throw new IllegalStateException("Missing parameter '" + parameterName + "' of type [" + parameterType.getName() + "]");
  }

  protected void raiseSessionRequiredException(String parameterName, Class<?> parameterType) throws HttpSessionRequiredException {
    throw new HttpSessionRequiredException("No HttpSession found for resolving parameter '" + parameterName + "' of type [" + parameterType.getName() + "]");
  }

	@Override	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(SessionAttribute.class);
	}

}