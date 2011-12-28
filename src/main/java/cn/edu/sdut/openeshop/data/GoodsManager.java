package cn.edu.sdut.openeshop.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.util.AnnotationLiteral;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;

import cn.edu.sdut.openeshop.controller.Deleted;
import cn.edu.sdut.openeshop.controller.Updated;
import cn.edu.sdut.openeshop.model.Goods;
import cn.edu.sdut.openeshop.model.GoodsImg;
import cn.edu.sdut.openeshop.tools.ImageUpload;

@Stateful
@ConversationScoped
@Named
public class GoodsManager {
	@PersistenceContext
	private EntityManager em;

	@Inject
	Logger log;
	
	@Inject ImageUpload imageUpload;
	
	@Inject @Any Event<Goods> goodsEvent;

	@Inject Conversation conversation;
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
		return null;
	}
	
	@Begin
	public void edit(){
		conversation.setTimeout(10 * 60 * 1000); // 10 minutes
	}

	@End
	public String save() {
		Set<GoodsImg> imgs = new HashSet<GoodsImg>(0);
		log.info("files:" + imageUpload.getFiles());
		log.info("goods=" + instance);
		if(imageUpload.getSize() > 0) {
			for(String file:imageUpload.getFiles()){
				GoodsImg img = new GoodsImg();
				img.setGoods(instance);
				img.setImageUrl(file);
				imgs.add(img);
				log.info("save good img:" + file);
			}
		}
		
		instance.setGoodsImgs(imgs);
		em.merge(instance);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("成功保存商品信息"));
		goodsEvent.select(new AnnotationLiteral<Updated>(){}).fire(instance);
		return "/admin/goods_list.jsf";
	}

	@End
	public String remove(Long id) {
		Goods goods = findGoods(id);
		em.remove(goods);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("成功删除商品信息"));
		goodsEvent.select(new AnnotationLiteral<Deleted>(){}).fire(goods);
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
