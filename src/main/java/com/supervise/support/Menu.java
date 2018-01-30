package com.supervise.support;

import java.util.List;

/**
 */
public class Menu {
	
	private String name;
	private String url;
	private int level;
	private String icon;
	private List<Menu> sub_menus;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<Menu> getSub_menus() {
		return sub_menus;
	}
	public void setSub_menus(List<Menu> sub_menus) {
		this.sub_menus = sub_menus;
	}
	
	@Override
	public String toString() {
		return "Menu [name=" + name + ", url=" + url + ", level=" + level
				+ ", icon=" + icon + ", sub_menus=" + sub_menus + "]";
	}
	
}
