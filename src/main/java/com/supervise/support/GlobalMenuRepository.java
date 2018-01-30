package com.supervise.support;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 */
@Repository
public class GlobalMenuRepository implements MenuRepository {

	protected List<Menu> repositories;

	@PostConstruct
	@Override
	public void initMenus() {
		try {
			Resource fileRource = new ClassPathResource("menu.md");
			InputStream in = fileRource.getInputStream();
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			repositories = JSON.parseArray(new String(buffer, "UTF-8"), Menu.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Menu> loadAllMenus() {
		return repositories;
	}

	@Override
	public List<Menu> loadUserMenus(long userId) {
		return loadAllMenus();
	}

}
