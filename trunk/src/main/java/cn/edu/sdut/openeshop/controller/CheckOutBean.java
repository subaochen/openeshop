package cn.edu.sdut.openeshop.controller;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Member;
import cn.edu.sdut.openeshop.model.Purchase;

@Stateful
@ConversationScoped
@Named("checkout")
public class CheckOutBean implements CheckOut {
	
	private String addr;
	private Purchase currentOrder;
	
	@PersistenceContext
	EntityManager em;
	@Inject Conversation conversation;
	@Inject ShoppingCart cart;
	@Inject @LoggedIn Member currentUser;
	@Inject Identity identity;
	

	@Override
	public String createOrder() {
		if(identity.isLoggedIn() == false) return "/login.jsf?redirect=true";
		
		conversation.begin();
		Purchase currentOrder = cart.getOrder();
		currentOrder.setAddr(addr);
		
		return "/checkout.jsf";
	}

	@Override
	public String submitOrder() {
		currentOrder.setMember(currentUser);
		em.merge(currentOrder);
		conversation.end();
		
		return "/orderdone.jsf?redirect=true";

	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
