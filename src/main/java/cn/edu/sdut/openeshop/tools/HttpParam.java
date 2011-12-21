package cn.edu.sdut.openeshop.tools;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.xml.ws.BindingType;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

@BindingType
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, METHOD, FIELD, PARAMETER })
public @interface HttpParam {
	@Nonbinding
	public String value();
}
