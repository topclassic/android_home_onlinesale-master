package com.example.home_onlinsale;

public class Product {
	private int id;
	private String title;
	private String desc;
	private Double price;
	private String saler;
	private String date;
	
	public Product(){}

	public Product(int id, String title, String desc, Double price,
			String saler, String date) {
		super();
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.price = price;
		this.saler = saler;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
