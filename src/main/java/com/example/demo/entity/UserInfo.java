package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(Include.NON_NULL)
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 8258628275681271953L;

	private int id;
	
	private String name;
	
	private int age;
	
	/**
	  * 不序列化
	 */
	@JsonIgnore
	private String pwd;
	
	/**
	  * 別名
	 */
	//@JsonInclude(Include.NON_NULL) // 空值不返回
	@JsonProperty("account")
	private String phone;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
	private Date createTime;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
	private Date updateTime;
	
}
