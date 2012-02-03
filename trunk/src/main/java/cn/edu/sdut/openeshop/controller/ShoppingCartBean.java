package cn.edu.sdut.openeshop.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Product;
import cn.edu.sdut.openeshop.model.Purchase;
import cn.edu.sdut.openeshop.model.PurchaseItem;

/**
 * 购物车组件
 * 
 * @author subaochen
 *
 */
@Stateful
@Named("cart")
@SessionScoped
public class ShoppingCartBean implements ShoppingCart {
	@Inject Logger log;

	Purchase order = new Purchase();

	@Override
	public boolean isEmpty() {
		return order.getPurchaseItems().isEmpty();
	}

	@Override
	public void addProduct(Product product, int num) {
		order.addProduct(product, num);
		log.info("adding product:" + product + ",num:" + num + ", current order:" + order);
		
	}

	@Override
	public List<PurchaseItem> getCart() {
		return order.getPurchaseItems();
	}


	@Override
	public BigDecimal getTotal() {
		return order.getTotalAmount();
	}


	@Override
	public void resetCart() {
		order = new Purchase();

	}

	@Override
	@Remove
	public void destroy() {}

}
