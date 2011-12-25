package cn.edu.sdut.openeshop.tools;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Goods;

@FacesConverter("goodsConverter")
public class GoodsConverter implements Converter{
    @PersistenceContext
	private EntityManager em;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
		String value) {
		return em.find(Goods.class,Long.parseLong(value));
	}
 
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return ((Goods) value).getId().toString();
	}	

}
