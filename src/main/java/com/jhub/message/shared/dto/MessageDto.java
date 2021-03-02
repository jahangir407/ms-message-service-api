package com.jhub.message.shared.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JGlobalMap
public class MessageDto {

	private String sender;
	private String content;
	private String timestamp;
}
