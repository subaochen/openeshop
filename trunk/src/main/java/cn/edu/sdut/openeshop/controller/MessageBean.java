package cn.edu.sdut.openeshop.controller;

import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import cn.edu.sdut.openeshop.model.Goods;
import cn.edu.sdut.openeshop.model.Member;

public class MessageBean {
	@Inject Logger log;
	
	/**
	 * 用户注册后的相关处理
	 * @param member
	 */
	public void onUserRegistered(@Observes @Registered Member member){
		// 比如发送欢迎邮件，发送站内短信等
		log.info("user:" + member.getUsername() +" registered");
	}
	
	public void onGoodsUpdated(@Observes @Updated Goods goods){
	    log.info("goods:" + goods + " updated");	
	}
	
	public void onGoodsDeleted(@Observes @Deleted Goods goods){
		log.info("goods:" + goods + " deleted");
	}

}
