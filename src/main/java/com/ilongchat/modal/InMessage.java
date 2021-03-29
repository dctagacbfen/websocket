package com.ilongchat.modal;

import java.util.Map;

public class InMessage {
	private String type;
	private Map<String,Object> data;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "InMessage [type=" + type + ", data=" + data + "]";
	}
}
