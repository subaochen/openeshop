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
import javax.enterprise.inject.Produces;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.controller.Deleted;
import cn.edu.sdut.openeshop.controller.Selected;
import cn.edu.sdut.openeshop.controller.Updated;
import cn.edu.sdut.openeshop.model.GoodsImg;
import cn.edu.sdut.openeshop.model.Product;
import cn.edu.sdut.openeshop.model.ProductImg;
import cn.edu.sdut.openeshop.tools.ImageUpload;

@Stateful
@RequestScoped
@Named
public class ProductManager {
	@PersistenceContext
	private EntityManager em;

	@Inject
	Logger log;
	
	@Inject ImageUpload imageUpload;
	
	@Inject Conversation conversation;

	@Inject
	@Any
	Event<Product> productEvent;

	private List<Product> resultList = new ArrayList<Product>(0);
	
	@Produces
	@ConversationScoped
	@Selected
	@Named
	private static Product selectedProduct;

	private Product instance = new Product();
	private Long productId;
	private String searchFor;

	// 如果该变量存在，则需要列出该商品下面的产品
	private Long goodsId;

	public void wire() {
		log.info("+++++++++++++++productManager wire is called,productId=" + productId);
		conversation.begin();
		if (getproductId() != null && getproductId() != 0)
			loadInstance(getproductId());
		
		// 设置impageUpload组件的productImgs属性，以便在上传组件中显示出已经存在的图片
		if(!instance.getProductImgs().isEmpty()){
			imageUpload.setProductImages(new ArrayList(instance.getProductImgs()));
		}
	}

	private void loadInstance(Long id) {
		selectedProduct = instance = em.find(Product.class, id);
	}

	public List<Product> getResultList() {
		log.info("searchFor=" + searchFor);
		if (resultList.size() != 0)
			return resultList;

		if (searchFor != null)
			return em
					.createQuery(
							"from Product product where lower(name) like lower(concat(concat('%',:name),'%'))")
					.setParameter("name", searchFor).getResultList();

		if (getGoodsId() != null && getGoodsId() != 0)
			return em
					.createQuery(
							"from Product product where product.goods.id=:goodsId")
					.setParameter("goodsId", getGoodsId()).getResultList();

		return em.createQuery("from Product product").getResultList();
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
		Set<ProductImg> imgs = new HashSet<ProductImg>(0);

		log.info("product=" + instance);
		// 知识点：新旧图片一起保存的奥秘
		if(imageUpload.getSize() > 0) {
			for(ProductImg img:imageUpload.getProductImages()){
				img.setProduct(instance);
				imgs.add(img);
				log.info("saving product img:" + img.getImageUrl());
			}
		}
		
		instance.setProductImgs(imgs);	
		em.merge(instance);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("成功保存产品信息"));
		productEvent.select(new AnnotationLiteral<Updated>() {
		}).fire(instance);
		
		conversation.end();
		return "/admin/product_list.jsf";
	}

	public String remove(Long id) {
		Product product = findproduct(id);
		em.remove(product);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("成功删除产品信息"));
		productEvent.select(new AnnotationLiteral<Deleted>() {
		}).fire(product);
		return "/admin/product_list.jsf";
	}

	public Product findproduct(Long id) {
		return em.find(Product.class, id);
	}

	public Product getInstance() {
		return instance;
	}

	public void setInstance(Product instance) {
		this.instance = instance;
	}

	public Long getproductId() {
		return productId;
	}

	public void setproductId(Long productId) {
		this.productId = productId;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
}
