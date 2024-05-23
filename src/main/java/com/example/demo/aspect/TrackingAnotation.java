package com.example.demo.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Aplicable solo a métodos
@Retention(RetentionPolicy.RUNTIME) // Disponible en tiempo de ejecución
public @interface TrackingAnotation {
	
}