package com.ea.crud.aop;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopLogging {
	protected static transient Log log = LogFactory.getLog(AopLogging.class);

	@Around("execution(* com.ea.crud.service.UserService.loadUserByUsername(..))")
	public Object logAfter(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("LOGIN ATTEMP:" + Arrays.toString(joinPoint.getArgs()));
		Object retVal = joinPoint.proceed();
        return retVal;
	}

}
