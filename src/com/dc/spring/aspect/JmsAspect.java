package com.dc.spring.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Repository;

@Repository("jmsAspect")
public class JmsAspect implements MethodInterceptor {


    public void asAround(ProceedingJoinPoint pj) throws Throwable{
        
		
		pj.getArgs();
		pj.getClass();
		pj.getKind();
		
		pj.getSourceLocation();
		System.out.println(pj.getSourceLocation().getWithinType());
		pj.getStaticPart();
		pj.proceed();
		
		System.out.println(pj.getSourceLocation().getWithinType());
	
		System.out.println("asAfter!");
		
    }


	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
		
		
		return null;
	}
}
