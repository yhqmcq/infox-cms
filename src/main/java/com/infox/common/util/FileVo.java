package com.infox.common.util;

public class FileVo {

	/** 文件上传的真实路径 */
	private String realPath;

	/** 文件上传的相对路径 */
	private String webPath;

	/** 新文件名称 */
	private String newName;

	/** 原文件名称 */
	private String srcName;

	/** 文件类型 */
	private String contentType;

	/** 文件大小 */
	private Long size;

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "FileVo [realPath=" + realPath + ", webPath=" + webPath + ", newName=" + newName + ", srcName=" + srcName + ", contentType=" + contentType + ", size=" + size + ", getRealPath()=" + getRealPath() + ", getWebPath()=" + getWebPath() + ", getNewName()=" + getNewName() + ", getSrcName()=" + getSrcName() + ", getContentType()=" + getContentType() + ", getSize()=" + getSize() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
