package cn.edu.sdut.openeshop.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "goods")
public class Goods implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String code;
	private String description;
	private int store;
	private Set<Product> products = new HashSet<Product>(0);
	private Set<GoodsImg> goodsImgs = new HashSet<GoodsImg>(0);

	@Id
	@SequenceGenerator(name = "goods_seq", sequenceName = "goods_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goods_seq")
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

	@OneToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY, mappedBy = "goods")
	@OrderBy("id ASC")
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", code=" + code
				+ ", description=" + description + ", store=" + store + "]";
	}

	@OneToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY, mappedBy = "goods")
	@OrderBy("id ASC")
	public Set<GoodsImg> getGoodsImgs() {
		return goodsImgs;
	}

	public void setGoodsImgs(Set<GoodsImg> goodsImgs) {
		this.goodsImgs = goodsImgs;
	}
	
	
}
