package cn.edu.sdut.openeshop.data;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Goods;

@Stateful
@RequestScoped
@Named
public class GoodsManager {
	@PersistenceContext
	private EntityManager em;
	
	@Inject Logger log;

	private Goods instance = new Goods();
	private Long goodsId;
	
	public void wire(){
		log.info("GoodsManager wire is called,goodsId=" + goodsId);
		if(getGoodsId() != null && getGoodsId() != 0) 
			loadInstance(getGoodsId());
	}
	
	private void loadInstance(Long id) {
		instance = em.find(Goods.class, id);
	}

	@Produces @Named
	public List<Goods> getAllGoods(){
		return em.createQuery("from Goods goods").getResultList();
	}
	
	public String save(){
		em.merge(instance);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功保存商品信息"));
		return "/admin/goods_list.jsf";
	}
	
	public String remove(Long id){
		em.remove(findGoods(id));
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功删除商品信息"));
		return "/admin/goods_list.jsf";
	}
	
	public Goods findGoods(Long id){
		return em.find(Goods.class, id);
	}

	public Goods getInstance() {
		return instance;
	}

	public void setInstance(Goods instance) {
		this.instance = instance;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
}
