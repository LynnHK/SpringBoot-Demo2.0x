package com.example.demo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendInfo {

	private String id;
	
	private String uin;
	
	private String userName;
	
	private String nickName;
	
	private String remarkName;
	
	private String headImgUrl;
	
	private int contactFlag;
	
	private String signature;
	
	private int verifyFlag;
	
	private int isOwner;
	
	private int sex;
	
	private String city;
	
	private String province;
	
	private int starFriend;
	
	private int delflag;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
	private Date dateCreated;
	
	private String createdBy;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
	private Date dateUpdated;
	
	private String updatedBy;
	
}
