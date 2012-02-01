package cn.edu.sdut.openeshop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String code;
	private String description;
	private int store;
	private BigDecimal price;
	private Goods goods;
	private Set<ProductImg> productImgs = new HashSet<ProductImg>(0);

	@Id
	@SequenceGenerator(name = "product_seq", sequenceName = "product_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStore() {
		return store;
	}

	public void setStore(int store) {
		this.store = store;
	}

	@Column(name = "price", precision = 10, scale = 2)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goods_id")
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	@OneToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY, mappedBy = "product")
	@OrderBy("id ASC")
	public Set<ProductImg> getProductImgs() {
		return productImgs;
	}

	public void setProductImgs(Set<ProductImg> productImgs) {
		this.productImgs = productImgs;
	}
	
	@Transient
	public String getMainImageUrl(){
		if(productImgs == null || productImgs.isEmpty()) return "";
		
		//TODO 取第一个图片作为产品的主图片
		for(ProductImg productImg:productImgs){
			return productImg.getImageUrl();
		}
		return "";
	}
	

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", code=" + code
				+ ", description=" + description + ", store=" + store
				+ ", price=" + price + "]";
	}
	
	
}
