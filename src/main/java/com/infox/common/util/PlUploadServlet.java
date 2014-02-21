package com.infox.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 上传工具
 * @author Administrator
 *
 */
public class PlUploadServlet extends HttpServlet {
	
	private static Logger log = LoggerFactory.getLogger(PlUploadServlet.class) ;

	private static final long serialVersionUID = 1L;

	public PlUploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() ;
		String crrentPath = request.getServletContext().getRealPath("");
		String parentPath = new File(crrentPath).getParent();
		String fileFolder = request.getParameter("fileFolder");// 前台传递过来的文件夹参数，如果有就在这个目录里面保存文件 ;
		String dateFolder = request.getParameter("dateFolder") ;//日期目录
		String isParent = request.getParameter("isParent") ; ;
		String realPath = "" ;//文件上传绝对路径
		String webPath = "" ;//WEB访问路径
		String srcName = null;// 上传到服务器的临时文件名
		String newName = null;// 最后合并后的新文件名
		
		//是否加上日期作为目录
		if(null != dateFolder && dateFolder.equalsIgnoreCase("true")) {
			dateFolder = File.separator + DateUtil.dateToString(new Date(), "yyyy") + "-" + DateUtil.dateToString(new Date(), "MM") + "-" + DateUtil.dateToString(new Date(), "dd") + File.separator ;// 日期命名的文件夹
		} else {
			dateFolder = File.separator ;
		}
		//自定义目录
		if (fileFolder == null || fileFolder.trim().equals("")) {
			fileFolder = "temp" + File.separator;// 避免前台没有传递这个参数的时候，会报错
		} else {
			fileFolder = fileFolder.replace("/", File.separator) ;
		}
		if(null == isParent || isParent.equalsIgnoreCase("true")) {//是否存放在当前项目的父目录中
			fileFolder = fileFolder.replace(File.separator+ConfigUtil.get(Constants.FILE_ROOT), "") ;
			realPath = parentPath + File.separator + ConfigUtil.get(Constants.FILE_ROOT) + fileFolder + dateFolder ;
			webPath = basePath + File.separator + ConfigUtil.get(Constants.FILE_ROOT) + fileFolder + dateFolder ;
		} else {//存放在当前项目目录中
			realPath = crrentPath + fileFolder + dateFolder ;
			webPath = basePath + request.getContextPath() + fileFolder + dateFolder ;
		}
		
		File up = new File(realPath);
		if (!up.exists()) {
			up.mkdirs();
		}
		
		FileVo f = new FileVo() ;
		Map<String,Object> m = new HashMap<String, Object>() ;
		m.put("status", false) ;
		m.put("msg", "文件上传失败！") ;
		
		response.setCharacterEncoding("UTF-8");
		Integer chunk = null;// 分割块数
		Integer chunks = null;// 总分割数
		BufferedOutputStream outputStream = null;
		
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024);
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");
				List<FileItem> items = upload.parseRequest(request);
				
				for (FileItem item : items) {
					f.setContentType(item.getContentType()) ;
					if (item.isFormField()) {// 是文本域
						if (item.getFieldName().equals("name")) {
							srcName = item.getString();
							//System.out.println("临时文件名：" + srcName);
						} else if (item.getFieldName().equals("chunk")) {
							chunk = Integer.parseInt(item.getString());
							//System.out.println("当前文件块：" + (chunk + 1));
						} else if (item.getFieldName().equals("chunks")) {
							chunks = Integer.parseInt(item.getString());
							//System.out.println("文件总分块：" + chunks);
						}
					} else {// 如果是文件类型
						if (srcName != null) {
							String chunkName = srcName;
							if (chunk != null) {
								chunkName = chunk + "_" + srcName;
							}
							File savedFile = new File(realPath, chunkName);
							item.write(savedFile);
						}
					}
				}

				newName = UUID.randomUUID().toString().replace("-", "").concat(".").concat(FilenameUtils.getExtension(srcName));
				
				if (chunks == null) {// 如果不分块上传，那么只有一个名称，就是临时文件的名称
					newName = srcName;
				}
				long size = 0;
				if (chunk != null && chunk + 1 == chunks) {
					outputStream = new BufferedOutputStream(new FileOutputStream(new File(realPath, newName)));
					// 遍历文件合并
					
					for (int i = 0; i < chunks; i++) {
						File tempFile = new File(realPath, i + "_" + srcName);
						size += Long.parseLong(tempFile.length()+"") ;
						
						byte[] bytes = FileUtils.readFileToByteArray(tempFile);
						outputStream.write(bytes);
						outputStream.flush();
						tempFile.delete();
					}
					outputStream.flush();
					
				}
				f.setSize(size) ;
				f.setNewName(newName) ;
				f.setSrcName(srcName) ;
				f.setRealPath(realPath) ;
				f.setWebPath(StringUtil.f2uPath(webPath) + newName) ;
				
				m.put("status", true) ;
				m.put("msg", "文件上传成功！") ;
				m.put("fileinfo", f) ;
			} catch (FileUploadException e) {
				log.info(e.getMessage()) ;
			} catch (Exception e) {
				log.info(e.getMessage()) ;
			} finally {
				try {
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					log.info(e.getMessage()) ;
				}
			}
		}
		response.getWriter().write(JSON.toJSONString(m));
	}
	
	
}
