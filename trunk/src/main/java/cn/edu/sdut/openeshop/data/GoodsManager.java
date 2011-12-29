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

import cn.edu.sdut.openeshop.controller.Deleted;
import cn.edu.sdut.openeshop.controller.Updated;
import cn.edu.sdut.openeshop.model.Goods;
import cn.edu.sdut.openeshop.model.GoodsImg;
import cn.edu.sdut.openeshop.tools.ImageUpload;

@Stateful
@RequestScoped
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
		conversation.begin();
		log.info("wiring,conversation id=" + conversation.getId());
		if (getGoodsId() != null && getGoodsId() != 0){
			loadInstance(getGoodsId());
			
			// 设置impageUpload组件的files属性，以便在上传组件中显示出已经存在的图片
			if(!instance.getGoodsImgs().isEmpty()){
				imageUpload.setFiles(new ArrayList(instance.getGoodsImgs()));
			}
		}
	}

	private void loadInstance(Long id) {
		instance = em.find(Goods.class, id);
	}

	public List<Goods> getResultList() {
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
	
	public String save() {
		log.info("saving,conversation id=" +conversation.getId());		
		Set<GoodsImg> imgs = new HashSet<GoodsImg>(0);

		log.info("goods=" + instance);
		// 知识点：新旧图片一起保存的奥秘
		if(imageUpload.getSize() > 0) {
			for(GoodsImg img:imageUpload.getFiles()){
				img.setGoods(instance);
				imgs.add(img);
				log.info("save goods img:" + img.getImageUrl());
			}
		}
		
		instance.setGoodsImgs(imgs);	
		em.merge(instance);
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("成功保存商品信息"));
		
		goodsEvent.select(new AnnotationLiteral<Updated>(){}).fire(instance);
		
		conversation.end();
		return "/admin/goods_list.jsf";
	}

	
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
	
	public void debug(){
		if(imageUpload != null && imageUpload.getSize() != 0)
			for(GoodsImg img:imageUpload.getFiles())
			    log.info("DEBUG:image uploaded file=" + img.getImageUrl());
	}
}
