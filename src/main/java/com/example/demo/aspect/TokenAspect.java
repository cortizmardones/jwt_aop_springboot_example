package com.example.demo.aspect;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenAspect {
	
	@Before("@annotation(TrackingAnotation)")
	public void beforeGetToken(JoinPoint joinPoint) {
		
		System.out.println("");
		System.out.println("################### Init Tracking ###################");
		System.out.println("Action AOP: @Before");
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		System.out.println("MethodName: ".concat(signature.getName()).concat("()"));
		
		String[] parameterNames = signature.getParameterNames();
        Class<?>[] parameterTypes = signature.getParameterTypes();
        Object[] args = joinPoint.getArgs();
		
        AtomicInteger counter = new AtomicInteger(0);
        Arrays.stream(parameterNames).forEach(parameter -> {
            System.out.println("Parameter " + (counter.get() +1) + ": ParameterName: " + parameter + ", ParameterType: " + parameterTypes[counter.get()].getSimpleName() + ", ParameterValue: " + args[counter.get()]);
            counter.getAndIncrement();
        });
		
		System.out.println("################### Finish Tracking ###################");
		System.out.println("");
	}
	
	
	@AfterReturning(value = "@annotation(TrackingAnotation)", returning = "result")
    //@AfterReturning(pointcut = "execution(* com.example.demo.service.*.*(..))", returning = "result")
    public void afterGetToken(JoinPoint joinPoint, Object result) {
    	
		System.out.println("");
		System.out.println("################### Init Tracking ###################");
		System.out.println("Action AOP: @AfterReturning");
    	
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        
        System.out.println("Method: ".concat(methodName).concat("()"));
        
        if (result != null) {
            System.out.println("Return: Type: " + result.getClass().getSimpleName() + " , Value: " + result);
        }
        
		System.out.println("################### Finish Tracking ###################");
		System.out.println("");
    }
    
    
    //ESTA ES MI FORMA (ME GUSTA MAS)
//	@Before("@annotation(TrackingAnotation)")
//	//@Before("execution(* com.example.demo.service.*.*(..))") Usando rutas especificas.
//	public void beforeGetToken(JoinPoint joinPoint) {
//		
//		System.out.println("");
//		System.out.println("################### Init Tracking ###################");
//		System.out.println("Action AOP: @Before");
//		
//		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//		System.out.println("MethodName: ".concat(signature.getName()).concat("()"));
//		
//        String[] parameterNames = signature.getParameterNames();
//        Arrays.stream(parameterNames).forEach(parameter -> System.out.println("ParameterName: " + parameter));
//		
//        Object[] args = joinPoint.getArgs();
//		Arrays.stream(args).forEach(argumento -> System.out.println("ParameterType: " + argumento.getClass().getSimpleName() + " , ParameterValue: " + argumento));
//		
//		System.out.println("################### Finish Tracking ###################");
//		System.out.println("");
//	}

}
