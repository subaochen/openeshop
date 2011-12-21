package cn.edu.sdut.openeshop.tools;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;

public class HttpParams {
	@Produces
	@HttpParam("")
	String getParamValue(InjectionPoint ip) {
		ServletRequest request = (ServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		return request.getParameter(ip.getAnnotated()
				.getAnnotation(HttpParam.class).value());
	}
}
