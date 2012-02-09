package cn.edu.sdut.openeshop.controller;

import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Address;
import cn.edu.sdut.openeshop.model.Member;
import cn.edu.sdut.openeshop.model.Purchase;

@Stateful
@ConversationScoped
@Named("checkout")
public class CheckOutBean implements CheckOut {
	
	private Address address;
	private Purchase currentOrder;
	
	@Inject Logger log;
	
	@PersistenceContext
	EntityManager em;
	@Inject Conversation conversation;
	@Inject ShoppingCart cart;
	@Inject @LoggedIn Member currentUser;
	@Inject Identity identity;
	
	public void wire(){
		conversation.begin();
		address = new Address();
	}
	

	@Override
	public String createOrder() {
		if(identity.isLoggedIn() == false) return "/login.jsf?redirect=true";		
		currentOrder = cart.getOrder();
		log.info("creating order:" + currentOrder + ",conversation id=" + conversation.getId());
		return "/checkout.jsf";
	}

	@Override
	public String submitOrder() {
		log.info("submiting order:" + currentOrder + ",conversation id=" + conversation.getId());
		currentOrder.setMember(currentUser);
		currentOrder.setAddr(address.getAddr());
		currentOrder.setAddrName(address.getAddrName());
		currentOrder.setAddrTel(address.getAddrTel());
		em.persist(currentOrder);
		
		// 清空购物车
		cart.resetCart();
		
		//conversation.end();

		return "/orderdone.jsf";
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public void setAddress(Address address) {
		this.address = address;
	}

}
