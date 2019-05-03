package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelayMission implements Serializable {
	
	private static final long serialVersionUID = -8857495246804414162L;

	/**
	 * 任务ID
	 */
	private String id;
	
	/**
	 * 任务类型
	 */
	private int type;
	
	/**
	 * 延迟时间（秒）
	 */
	private long delayTime;

	/**
	 * 数据
	 */
	private Object data;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 创建者
	 */
	private String createBy;
	
}
