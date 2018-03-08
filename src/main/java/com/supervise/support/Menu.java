package com.supervise.support;

import lombok.Data;

import java.util.List;

/**
 */
@Data
public class Menu {
	
	private String name;
	private String url;
	private int level;
	private String icon;
	private boolean checkDep;
	private List<Menu> sub_menus;

	public Menu() {
	}

	@Override
	public String toString() {
		return "Menu [name=" + name + ", url=" + url + ", level=" + level
				+ ", icon=" + icon + ", sub_menus=" + sub_menus + "]";
	}
	
}
