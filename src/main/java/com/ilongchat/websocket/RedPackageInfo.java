package com.ilongchat.websocket;

public class RedPackageInfo {
	private int num;
	private String detail;
	private double point;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public double getPoint() {
		return point;
	}
	public void setPoint(double point) {
		this.point = point;
	}
	@Override
	public String toString() {
		return "RedPackageInfo [num=" + num + ", detail=" + detail + ", point="
				+ point + "]";
	}
	
	
}
