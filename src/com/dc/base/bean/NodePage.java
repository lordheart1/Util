package com.dc.base.bean;

import java.util.List;
import java.util.Map;

public class NodePage {
	
	/**
	 * 当前页数
	 */
	private int p = 1;
	
	/**
	 * 分页长度
	 */
	private int pageSize;
	
	/**
	 * 统计总数
	 */
	private long count;
	
	/**
	 * 分页数据
	 */
	private List data;
	
	/**
	 * 统计数据
	 */
	private Map footer;

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Map getFooter() {
		return footer;
	}

	public void setFooter(Map footer) {
		this.footer = footer;
	}

	
	

}
