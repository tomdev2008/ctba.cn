package cn.ctba.equipment.web.struts.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * 装备上传表单
 * 
 * @author gladstone
 * @since Mar 7, 2009
 */
@SuppressWarnings("serial")
public class WareForm extends ActionForm {

	private String name;
	private String discription;
	private String brand;
	private String brandOther;
	private String price;
	private String saleTime;
	private String shortname;
	private String num;
	private String keyword;
	private String tech;
	private String officailprice;
	private FormFile imageFile;
	private String link;
	private Integer type;
	private Integer sextype;
	private Integer categoryId;
	private Integer shopId;
	private String behalf;

	public String getBehalf() {
		return behalf;
	}

	public String getBrand() {
		return brand;
	}

	public String getDiscription() {
		return discription;
	}

	public FormFile getImageFile() {
		return imageFile;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getLink() {
		return link;
	}

	public String getName() {
		return name;
	}

	public String getNum() {
		return num;
	}

	public String getOfficailprice() {
		return officailprice;
	}

	public String getPrice() {
		return price;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public Integer getSextype() {
		return sextype;
	}

	public String getShortname() {
		return shortname;
	}

	public String getTech() {
		return tech;
	}

	public Integer getType() {
		return type;
	}

	public void setBehalf(String behalf) {
		this.behalf = behalf;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setOfficailprice(String officailprice) {
		this.officailprice = officailprice;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public void setSextype(Integer sextype) {
		this.sextype = sextype;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getBrandOther() {
		return brandOther;
	}

	public void setBrandOther(String brandOther) {
		this.brandOther = brandOther;
	}

}
