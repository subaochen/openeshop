package cn.edu.sdut.openeshop.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import cn.edu.sdut.openeshop.model.Product;
import cn.edu.sdut.openeshop.model.Purchase;
import cn.edu.sdut.openeshop.model.PurchaseItem;

/**
 * 购物车
 * 
 * 参考了dvdstore中的购物车设计
 * @author subaochen
 *
 */
public interface ShoppingCart {

	public Purchase getOrder();
	public boolean getIsEmpty();
	public void addProduct(Product product, int num);
	public List<PurchaseItem> getCart();
	
	public BigDecimal getTotalAmount();
	
	public void updateCart();
	public void resetCart();
	
	public void destroy();
}
