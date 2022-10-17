package com.help.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggerAspect {

	@Around("execution(* com.help.board..controller.*Controller.*(..)) or execution(* com.help.board..service.*Service.*(..)) or execution(* com.help.board..mapper.*Mapper.*(..)) or execution(* com.help.board..repository.*Repository.*(..)) or execution(* com.help.admin..repository.*Repository.*(..)) or execution(* com.help.admin..controller.*Controller.*(..)) or execution(* com.help.admin..service.*Service.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();

		if (name.contains("Controller") == true) {
			type = "Controller ===> ";
		} else if (name.contains("Service") == true) {
			type = "Service ===> ";
		} else if (name.contains("Mapper") == true) {
			type = "Mapper ===> ";
		} else if (name.contains("Repository") == true) {
			type = "Repository ===> ";
		}


		log.error(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}

}