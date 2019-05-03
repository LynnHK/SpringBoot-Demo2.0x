package com.example.demo.constant;

import com.alibaba.fastjson.JSON;

public enum DelayMissionType {
	
	ORDER(1, "订单延时取消");
	
	private int type;
	
	private String desc;
	
	private DelayMissionType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
