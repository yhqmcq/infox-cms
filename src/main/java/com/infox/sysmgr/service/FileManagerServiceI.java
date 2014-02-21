package com.infox.sysmgr.service;

import java.util.List;

import com.infox.sysmgr.web.form.FileWrapForm;


public interface FileManagerServiceI {

	/**
	 * 获取文件树和列表
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List<FileWrapForm> treeListFile(FileWrapForm form) throws Exception ;
	
	public void createDir(FileWrapForm form) throws Exception ;
	
	public int deleteFF(FileWrapForm form) throws Exception ;
	
	public void copyFF(FileWrapForm form) throws Exception ;
	
	public void moveFF(FileWrapForm form) throws Exception ;
	
	public void rename(FileWrapForm form) throws Exception ;
	
}
