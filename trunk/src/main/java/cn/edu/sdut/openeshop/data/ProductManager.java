package cn.edu.sdut.openeshop.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.controller.Deleted;
import cn.edu.sdut.openeshop.controller.Updated;
import cn.edu.sdut.openeshop.model.Product;

@Stateful
@RequestScoped
@Named
public class ProductManager {
	@PersistenceContext
	private EntityManager em;

	@Inject
	Logger log;
	
	@Inject @Any Event<Product> productEvent;

	private List<Product> resultList = new ArrayList<Product>(0);

	private Product instance = new Product();
	private Long productId;
	private String searchFor;

	public void wire() {
		log.info("productManager wire is called,productId=" + productId);
		if (getproductId() != null && getproductId() != 0)
			loadInstance(getproductId());
	}

	private void loadInstance(Long id) {
		instance = em.find(Product.class, id);
	}

	public List<Product> getResultList() {
		log.info("searchFor=" + searchFor);
		if (resultList.size() != 0)
			return resultList;
		if (searchFor != null)
			return em
					.createQuery(
							"from Product product where lower(name) like lower(concat(concat('%',:name),'%'))")
					.setParameter("name", searchFor).getResultList();
		return em.createQuery("from Product product").getResultList();
	}

	/**
	 * 根据名称搜索
	 * 
	 * @return
	 */
	public String search() {
		return null;
	}

	public String save() {
		em.merge(instance);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("成功保存产品信息"));
		productEvent.select(new AnnotationLiteral<Updated>(){}).fire(instance);
		return "/admin/product_list.jsf";
	}

	public String remove(Long id) {
		Product product = findproduct(id);
		em.remove(product);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("成功删除产品信息"));
		productEvent.select(new AnnotationLiteral<Deleted>(){}).fire(product);
		return "/admin/product_list.jsf";
	}

	public Product findproduct(Long id) {
		return em.find(Product.class, id);
	}

	public Product getInstance() {
		return instance;
	}

	public void setInstance(Product instance) {
		this.instance = instance;
	}

	public Long getproductId() {
		return productId;
	}

	public void setproductId(Long productId) {
		this.productId = productId;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
}
