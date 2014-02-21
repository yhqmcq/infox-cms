package com.infox.common.web.page;

import java.util.List;

/**
 * easyui的tree模型
 * @author Administrator
 *
 */
public class Tree<T> {

	private String id ;
	
	private String text ;
	
	private String state ;
	
	private boolean checked = false;
	
	private Object attributes ;
	
	private List<T> children ;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}

}
