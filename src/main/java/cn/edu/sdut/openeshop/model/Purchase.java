package cn.edu.sdut.openeshop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String addrName;
	private String addrTel;
	private String addr;
	private String shipStatus;
	private String payStatus;
	private String status;// 订单状态，enabled,disabled
	private Member member;
	private BigDecimal totalAmount;
	private String shipNo;
	private Date timeCreated;
	private Date timeShipped;
	private List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>(0);
	
	public Purchase(){
	}

	@Id
	@SequenceGenerator(name = "purchase_seq", sequenceName = "purchase_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="addr_name")
	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	@Column(name="addr_tel")
	public String getAddrTel() {
		return addrTel;
	}

	public void setAddrTel(String addrTel) {
		this.addrTel = addrTel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name="ship_status")
	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}

	@Column(name="pay_status")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@OneToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY, mappedBy = "purchase")
	@OrderBy("id ASC")
	public List<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}
	
	/**
	 * 向订单中增加产品
	 */
	public void addProduct(Product product, int num){
		for(PurchaseItem item:purchaseItems){
			if(item.getProduct().getId() == product.getId()){
				item.addQuantity(num);
				return;
			}
		}
		
		PurchaseItem item = new PurchaseItem();
		item.setProduct(product);
		item.setNum(num);
		item.setPurchase(this);
		purchaseItems.add(item);
		System.out.println("addProduct:" + product + ",num:" + num);
	}

	@Column(name="total_amount",precision=10,scale=2)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name="ship_no")
	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	
	
	public void caculateTotal(){
		totalAmount = BigDecimal.ZERO;
		for(PurchaseItem item:purchaseItems){
			totalAmount = totalAmount.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getNum())));
		}
		System.out.println("--------------------totalAmount=" + totalAmount);
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", addrName=" + addrName + ", addrTel="
				+ addrTel + ", addr=" + addr + ", shipStatus=" + shipStatus
				+ ", payStatus=" + payStatus + ", member=" + member
				+ ", totalAmount=" + totalAmount + ", shipNo=" + shipNo
				+ ", purchaseItems size=" + purchaseItems.size() + "]";
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time_created", length = 29)
	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time_shipped", length = 29)
	public Date getTimeShipped() {
		return timeShipped;
	}

	public void setTimeShipped(Date timeShipped) {
		this.timeShipped = timeShipped;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
