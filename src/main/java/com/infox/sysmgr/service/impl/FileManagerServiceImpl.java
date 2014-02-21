package com.infox.sysmgr.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infox.common.util.Constants;
import com.infox.common.util.StringUtil;
import com.infox.common.web.springmvc.RealPathResolver;
import com.infox.sysmgr.service.FileManagerServiceI;
import com.infox.sysmgr.web.form.FileWrapForm;
import com.infox.sysmgr.web.form.FileWrapForm.FileComparator;

@Service
public class FileManagerServiceImpl implements FileManagerServiceI {
	
	private static final Logger logger = LoggerFactory.getLogger(FileManagerServiceImpl.class);
	
	@Autowired
	private RealPathResolver realPathResolver ;

	@Override
	public List<FileWrapForm> treeListFile(FileWrapForm form) throws Exception {
		//文件管理的根目录
		String filePath = getParent() + (null==form.getPath()||"".equals(form.getPath()) ? File.separator+Constants.FILE_ROOT : form.getPath()) ;
		
		List<FileWrapForm> list = new ArrayList<FileWrapForm>() ;
		
		File root = new File(filePath) ;
		if(root.exists()) {
			File[] listFiles = root.listFiles() ;
			Arrays.sort(listFiles, new FileComparator());
			
			for(int i=0; i<listFiles.length; i++) {
				File file = listFiles[i] ;
				
				FileWrapForm fileForm = new FileWrapForm() ;
				fileForm.setId((i+100)+"") ;
				fileForm.setText(file.getName()) ;
				fileForm.setFileName(file.getName()) ;
				fileForm.setPath(StringUtil.f2uPath(getRelativePath(file.getAbsolutePath()))) ;
				fileForm.setLastModified((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(file.lastModified())) ;
				
				if(file.isDirectory()) {
					fileForm.setState("closed") ; 
					fileForm.setDir(true) ;
					fileForm.setIconCls("folder") ;
					fileForm.setFileSize(file.list().length+"个") ;
				} else {
					String ext = FilenameUtils.getExtension(file.getName()) ;
					fileForm.setIconCls(ext.toLowerCase()) ;
					fileForm.setEditable(FileWrapForm.editableExt(ext)) ;
					fileForm.setFileSize(StringUtil.formatSize(file.length())) ;
				}
				
				Map<String, String> attributes = new HashMap<String, String>() ;
				attributes.put("filePath", StringUtil.f2uPath(getRelativePath(file.getAbsolutePath()))) ;
				fileForm.setAttributes(attributes) ;
				
				list.add(fileForm) ;
			}
		}
		
		return list;
	}
	
	/**
	 * 获取当前环境的父路径
	 * @return
	 */
	public String getParent() {
		return new File(this.realPathResolver.get(File.separator)).getParent() ;
	}
	
	@Override
	public void createDir(FileWrapForm form) throws Exception {
		File file = new File(getParent() + form.getPath() + File.separator + form.getDirName()) ;
		if(!file.exists()) {
			file.mkdir() ;
		} else {
			throw new Exception("该目录文件夹已存在！") ;
		}
	}

	@Override
	public int deleteFF(FileWrapForm form) throws Exception {
		int count = 0;
		if(null != form.getPath() && !"".equals(form.getPath())) {
			File file = null ;
			
			String[] paths = form.getPath().split(",") ;
			for (String path : paths) {
				file = new File(getParent() + path) ;
				if(file.exists()) {
					if(FileUtils.deleteQuietly(file)) {
						count++ ;
						logger.info("[删除文件："+file.getAbsolutePath()+"]") ;
					}
				} else {
					throw new Exception("文件或目录不存在！") ;
				}
			}
		}
		return count ;
	}
	
	@Override
	public void rename(FileWrapForm form) throws Exception {
		File orig = new File(getParent()+form.getPath()+File.separator+form.getOrigName());
		File dest = new File(getParent()+form.getPath()+File.separator+form.getDestName());
		orig.renameTo(dest);
	}
	
	public static void main(String[] args) {
		File file = new File("c:/crm.htsml") ;
		if(file.exists()) {
			boolean deleteQuietly = FileUtils.deleteQuietly(file) ;
			System.out.println(deleteQuietly);
		} else {
		}
	}

	@Override
	public void copyFF(FileWrapForm form) throws Exception {
		
	}

	@Override
	public void moveFF(FileWrapForm form) throws Exception {
		
	}
	
	/**
	 * 获取文件管理相对路径
	 * @param absPath
	 * @return
	 */
	private String getRelativePath(String absPath) {
		if(null != absPath && !"".equals(absPath)) {
			return absPath.substring(absPath.indexOf(File.separator+Constants.FILE_ROOT)) ;
		} else {
			return "" ;
		}
	}
}
