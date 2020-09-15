package com.ly.local.common.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 用户表Entity
 * @author fu.zhou
 * @version 2020-08-28
 */
@Data
public class SoundVideoAuth{
	
	private static final long serialVersionUID = 1L;
	private Long id;		// 

	private String openId;		// open_id

	private String unionId;		// union_id

	private String memberId;		// 用户memberId

	private Date createTime;		// create_time

	private Date updateTime;		// update_time

	private Integer status;		// 状态 0-未授权 1-已授权

	
}