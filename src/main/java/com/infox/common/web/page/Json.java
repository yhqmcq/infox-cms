package com.infox.common.web.page;

public class Json implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 是否成功 */
	private boolean status = false;
	
	/** 提示信息 */
	private String msg = "";
	
	/** 其他信息 */
	private Object obj = null;


	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
