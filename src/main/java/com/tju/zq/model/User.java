package com.tju.zq.model;

import lombok.Data;

import java.util.Date;
@Data
public class User extends BaseObject {
	public User() {
	}

	public User(User u) {
		this.id = u.id;
		this.userName = u.userName;
		this.password = u.password;
		this.realName = u.realName;
		this.business = u.business;
		this.email = u.email;
		this.headPicture = u.headPicture;
		this.addDate = u.addDate;
		this.updateDate = u.updateDate;
		this.state = u.state;
		this.discount = u.discount;
	}


	private int id;
	private String userName;
	private String password;
	private String realName;
	private String business;
	private String email;
	private String headPicture;
	private Date addDate;
	private Date updateDate;
	private int state;
	private int discount;

	public void setDiscount(int discount) {
		this.discount = discount;
	}
}
