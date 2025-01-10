/*
 * VER : v0.1
 * PROJ : OPEN API
 * Copyright 2020 SITHA.RUPP All rights reserved
 *---------------------------------------------------------------------------------------------------------------
 *                               H      I      S      T      O      R      Y
 *---------------------------------------------------------------------------------------------------------------
 *   DATE            AUTHOR               DESCRIPTION
 *-----------    ------------------    --------------------------------------------------------------------------
 * 2020-06-01        SOPHEAP SITHA         Creation
 *---------------------------------------------------------------------------------------------------------------
 */
 
package sitha.rupp.platform.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CBC {

}
