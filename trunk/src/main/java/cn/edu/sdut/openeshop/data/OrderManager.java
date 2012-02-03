package cn.edu.sdut.openeshop.data;

import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.controller.ShoppingCart;
import cn.edu.sdut.openeshop.model.Product;

@Named
@RequestScoped
@Stateful
public class OrderManager {

	@Inject Logger log;
	@Inject ShoppingCart cart;
	
	public void addToCart(Product product){
		log.info("adding product:" + product + " to cart");
		cart.addProduct(product, 1);
	}
}
