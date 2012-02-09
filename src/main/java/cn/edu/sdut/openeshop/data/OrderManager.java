package cn.edu.sdut.openeshop.data;

import java.util.ArrayList;
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

	public void addToCart(Product product) {
		log.info("adding product:" + product + " to cart");
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
}
