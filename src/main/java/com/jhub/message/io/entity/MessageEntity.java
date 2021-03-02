package com.jhub.message.io.entity;

import com.googlecode.jmapper.annotations.JGlobalMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JGlobalMap
public class MessageEntity {

	private String sender;
	private String content;
	private String timestamp;

}
