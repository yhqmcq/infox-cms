package com.infox.sysmgr.web.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infox.common.util.Constants;
import com.infox.common.util.DateUtil;
import com.infox.common.util.FileUtil;
import com.infox.common.util.StringUtil;
import com.infox.common.util.ZipUtils;
import com.infox.common.web.BaseController;
import com.infox.common.web.page.Json;
import com.infox.common.web.springmvc.RealPathResolver;
import com.infox.sysmgr.service.FileManagerServiceI;
import com.infox.sysmgr.web.form.FileWrapForm;

@Controller
@RequestMapping("/sysmgr/filemanager")
public class FileManager extends BaseController {

	@Autowired
	private FileManagerServiceI fileservice;
	
	@Autowired
	private RealPathResolver realPathResolver ;
	
	@RequestMapping("/file_main.do")
	public String file_main(HttpServletRequest request) throws Exception {
		request.setAttribute("root", "/"+Constants.FILE_ROOT) ;
		return  Constants.SYSTEM + "file_main" ;
	}
	
	@RequestMapping("/upload.do")
	public String upload(FileWrapForm form, HttpServletRequest request) throws Exception {
		request.setAttribute("upload_path", form.getPath()) ;
		return  Constants.SYSTEM + "file_upload" ;
	}
	
	/**
	 * 获取文件管理根目录树
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file_treelist.do")
	@ResponseBody
	public List<FileWrapForm> file_treelist(FileWrapForm form) throws Exception {
		try {
			return this.fileservice.treeListFile(form) ;
		} catch (Exception e) {
			e.printStackTrace();
			throw e ;
		}
	}
	
	/**
	 * 创建目录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file_createDir.do")
	@ResponseBody
	public Json file_createDir(FileWrapForm form) throws Exception {
		Json json = new Json() ;
		try {
			this.fileservice.createDir(form) ;
			json.setStatus(true) ;
		} catch (Exception e) {
			json.setMsg(e.getMessage()) ;
		}
		return json ;
	}
	
	/**
	 * 删除目录或文件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file_deleteff.do")
	@ResponseBody
	public Json file_delff(FileWrapForm form) throws Exception {
		Json json = new Json() ;
		try {
			int deleteFF = this.fileservice.deleteFF(form) ;
			json.setMsg("总共删除文件或目录["+deleteFF+"]个") ;
			json.setStatus(true) ;
		} catch (Exception e) {
			json.setMsg(e.getMessage()) ;
		}
		return json ;
	}
	
	/**
	 * 复制文件或目录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file_copyff.do")
	@ResponseBody
	public Json file_copy(FileWrapForm form) throws Exception {
		Json json = new Json() ;
		try {
			this.fileservice.copyFF(form) ;
			json.setStatus(true) ;
		} catch (Exception e) {
			json.setMsg(e.getMessage()) ;
		}
		return json ;
	}
	
	/**
	 * 移动文件或目录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/file_moveff.do")
	@ResponseBody
	public Json file_move(FileWrapForm form) throws Exception {
		Json json = new Json() ;
		try {
			this.fileservice.moveFF(form) ;
			json.setStatus(true) ;
		} catch (Exception e) {
			json.setMsg(e.getMessage()) ;
		}
		return json ;
	}
	
	
	/**
	 * 下载文件
	 * @param fileName
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/download.do")
	public void download(FileWrapForm form ,HttpServletResponse response) throws Exception {
		try {
			File[] srcfiles = zipFiles(form) ;
			if(null != srcfiles && srcfiles.length > 0) {
				String zipName = form.getPath().substring(form.getPath().lastIndexOf("/")+1) + Constants.FILE_SUFFIX_ZIP ;
				if(srcfiles.length == 1){
					zipName=srcfiles[0].getName().substring(0, srcfiles[0].getName().lastIndexOf(".")) + Constants.FILE_SUFFIX_ZIP;
					if("".equals(zipName.substring(0, zipName.lastIndexOf(".")))) {
						zipName = DateUtil.formatI(new Date()) + zipName ;
					}
				}
				
				String zipPath = System.getProperty("java.io.tmpdir") + File.separator + zipName ;
				//ZIP压缩
				boolean flag = ZipUtils.zipFiles(srcfiles, new File(zipPath)) ;
				if(flag) {
					FileUtil.downFile(zipName, zipPath, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private File[] zipFiles(FileWrapForm form) {
		File[] files = null ;
		int fileCount = 0 ;
		
		if(null != form.getFileName() && !"".equals(form.getFileName())) {
			String absPath = new File(this.realPathResolver.get(File.separator)).getParent()  ;
			
			String[] fileNames = form.getFileName().split(",") ;
			
			for (int i = 0; i < fileNames.length; i++) {
				File file = new File(StringUtil.u2fPath(absPath + form.getPath() + File.separator + fileNames[i])) ;
				if(file.isFile()) {
					fileCount ++ ;
				}
			}
			files = new File[fileCount] ;
			fileCount = 0 ;
			for (int i=0; i<fileNames.length; i++) {
				File file = new File(StringUtil.u2fPath(absPath + form.getPath() + File.separator + fileNames[i])) ;
				if(file.isFile()) {
					files[fileCount++] = file ;
				}
			}
		}
		return files ;
	}
	
	
}
