package cn.edu.sdut.openeshop.data;

import java.util.ArrayList;
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
import javax.persistence.Query;

import cn.edu.sdut.openeshop.model.Goods;

@Stateful
@RequestScoped
@Named
public class GoodsManager {
	@PersistenceContext
	private EntityManager em;

	@Inject
	Logger log;

	private List<Goods> resultList = new ArrayList<Goods>(0);

	private Goods instance = new Goods();
	private Long goodsId;
	private String searchFor;

	public void wire() {
		log.info("GoodsManager wire is called,goodsId=" + goodsId);
		if (getGoodsId() != null && getGoodsId() != 0)
			loadInstance(getGoodsId());
	}

	private void loadInstance(Long id) {
		instance = em.find(Goods.class, id);
	}

	public List<Goods> getResultList() {
		log.info("searchFor=" + searchFor);
		if (resultList.size() != 0)
			return resultList;
		if (searchFor != null)
			return em
					.createQuery(
							"from Goods goods where lower(name) like lower(concat(concat('%',:name),'%'))")
					.setParameter("name", searchFor).getResultList();
		return em.createQuery("from Goods goods").getResultList();
	}

	/**
	 * 根据名称搜索
	 * 
	 * @return
	 */
	public String search() {
		return "/admin/goods_list.jsf";
	}

	public String save() {
		em.merge(instance);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("成功保存商品信息"));
		return "/admin/goods_list.jsf";
	}

	public String remove(Long id) {
		em.remove(findGoods(id));
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("成功删除商品信息"));
		return "/admin/goods_list.jsf";
	}

	public Goods findGoods(Long id) {
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

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
}
