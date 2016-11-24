package org.zk.entity;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username")
	private String userName;

	@Column
	private Integer age;

//	@Enumerated(EnumType.STRING)
//	private Sex sex;

//	@Lob
//	@Basic(fetch = FetchType.LAZY)
//	private byte[] photo;
	
//	@Formula("(select 12 from dual)")
//	private Integer avgScore;
//
//	private FieldHandler fieldHandler;
	
	

//	public Integer getAvgScore() {
//		return avgScore;
//	}
//
//	public void setAvgScore(Integer avgScore) {
//		this.avgScore = avgScore;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

//	public Sex getSex() {
//		return sex;
//	}
//
//	public void setSex(Sex sex) {
//		this.sex = sex;
//	}

//	public byte[] getPhoto() {
//		if (photo != null) {
//			return photo;
//		}
//
//		if (fieldHandler != null) {
//			return (byte[]) fieldHandler.readObject(this, "photo", photo);
//		} else {
//			return null;
//		}
//	}
//
//	public void setPhoto(byte[] photo) {
//		this.photo = photo;
//	}
//
//	public void setFieldHandler(FieldHandler handler) {
//		this.fieldHandler = handler;
//		System.out.println("setFieldHandler");
//	}
//
//	public FieldHandler getFieldHandler() {
//		System.out.println("getFieldhandler");
//		return this.fieldHandler;
//	}

}
