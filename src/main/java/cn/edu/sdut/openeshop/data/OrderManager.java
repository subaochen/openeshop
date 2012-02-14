package cn.edu.sdut.openeshop.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.controller.LoggedIn;
import cn.edu.sdut.openeshop.controller.ShoppingCart;
import cn.edu.sdut.openeshop.model.Member;
import cn.edu.sdut.openeshop.model.Product;
import cn.edu.sdut.openeshop.model.Purchase;

@Named
@RequestScoped
@Stateful
public class OrderManager {

	@PersistenceContext
	private EntityManager em;

	@Inject
	Logger log;
	@Inject
	ShoppingCart cart;

	@Inject
	@LoggedIn
	Member currentUser;
	
	private String searchFor;
	
	private List<Purchase> resultList = new ArrayList<Purchase>(0);

	public void addToCart(Product product) {
		log.info("---------------------------adding product:" + product + " to cart");
		cart.addProduct(product, 1);
	}

	public List<Purchase> getMyOrders() {
		if (currentUser == null)
			return new ArrayList<Purchase>(0);

		List<Purchase> list = em
				.createQuery(
						"from Purchase purchase where purchase.member.id=:memberId")
				.setParameter("memberId", currentUser.getId()).getResultList();
		return list;
	}
	
	public List<Purchase> getResultList() {
		log.info("searchFor=" + searchFor);
		if (resultList.size() != 0)
			return resultList;

		if (searchFor != null)
			return em
					.createQuery(
							"from Purchase purchase where lower(addr) like lower(concat(concat('%',:addr),'%'))")
					.setParameter("addr", searchFor).getResultList();


		return em.createQuery("from Purchase purchase").getResultList();
	}
	
	/**
	 * 发货订单
	 * @param id
	 */
	public void ship(Long id){
		Purchase order = findOrder(id);
		order.setTimeShipped(new Date());
		order.setShipStatus("ORDER_SHIPPED");
		em.persist(order);
	}
	
	/**
	 * 作废订单
	 * @param id
	 */
	public void discard(Long id){
		Purchase order = findOrder(id);
		order.setStatus("DISABLED");
		em.persist(order);		
	}
	
	public Purchase findOrder(Long id){
		return em.find(Purchase.class, id);
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
}
