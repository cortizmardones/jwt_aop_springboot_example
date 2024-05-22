package com.example.demo.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAspect {
	
	@Before("execution(* com.example.demo.service.*.*(..))")
	public void beforeGetToken(JoinPoint joinPoint) {
		
        String methodName = joinPoint.getSignature().getName();
        
        Object[] args = joinPoint.getArgs();
		
        System.out.println("Tracking: ");
		System.out.println("Action AOP: @Before");
		System.out.println("Method: ".concat(methodName).concat("()"));
		Arrays.stream(args).forEach(argumento -> System.out.println("Args: Type: " + argumento.getClass().getSimpleName() + " , Value: " + argumento));
		System.out.println("");
	}
	
	
    @AfterReturning(pointcut = "execution(* com.example.demo.service.*.*(..))", returning = "result")
    public void afterGetToken(JoinPoint joinPoint, Object result) {
    	
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        
        System.out.println("Tracking: ");
        System.out.println("Action AOP: @AfterReturning");
        System.out.println("Method: ".concat(methodName).concat("()"));
        
        if (result != null) {
            System.out.println("Return: Type: " + result.getClass().getSimpleName() + " , Value: " + result);
        } else {
            System.out.println("Return: null");
        }
        
        System.out.println("");
    }

}
