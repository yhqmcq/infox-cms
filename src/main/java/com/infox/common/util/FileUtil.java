package com.infox.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 文件操作类
 * @author 杨浩泉
 *
 */
public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private static final int BUFFER = 1024;
	
	/**
	 * 功 能: 拷贝文件(只能拷贝文件)
	 * 
	 * @param strSourceFileName
	 *            指定的文件全路径名
	 * @param strDestDir
	 *            拷贝到指定的文件夹
	 * @return 如果成功true;否则false
	 */
	public boolean copyTo(String strSourceFileName, String strDestDir) {
		File fileSource = new File(strSourceFileName);
		File fileDest = new File(strDestDir);

		// 如果源文件不存或源文件是文件夹
		if (!fileSource.exists() || !fileSource.isFile()) {
			logger.debug("源文件[" + strSourceFileName + "],不存在或是文件夹!");
			return false;
		}

		// 如果目标文件夹不存在
		if (!fileDest.isDirectory() || !fileDest.exists()) {
			if (!fileDest.mkdirs()) {
				logger.debug("目录文件夹不存，在创建目标文件夹时失败!");
				return false;
			}
		}

		try {
			String strAbsFilename = strDestDir + File.separator + fileSource.getName();

			FileInputStream fileInput = new FileInputStream(strSourceFileName);
			FileOutputStream fileOutput = new FileOutputStream(strAbsFilename);

			logger.debug("开始拷贝文件:");

			int count = -1;

			long nWriteSize = 0;
			long nFileSize = fileSource.length();

			byte[] data = new byte[BUFFER];

			while (-1 != (count = fileInput.read(data, 0, BUFFER))) {

				fileOutput.write(data, 0, count);

				nWriteSize += count;

				long size = (nWriteSize * 100) / nFileSize;
				long t = nWriteSize;

				String msg = null;

				if (size <= 100 && size >= 0) {
					msg = "/r拷贝文件进度:   " + size + "%   /t" + "/t   已拷贝:   " + t;
					logger.debug(msg);
				} else if (size > 100) {
					msg = "/r拷贝文件进度:   " + 100 + "%   /t" + "/t   已拷贝:   " + t;
					logger.debug(msg);
				}

			}

			fileInput.close();
			fileOutput.close();

			logger.debug("拷贝文件成功!");
			return true;

		} catch (Exception e) {
			logger.debug("异常信息：{}",e.getMessage()) ;
			return false;
		}
	}

	/**
	 * 删除指定的文件
	 * 
	 * @param strFileName
	 *            指定绝对路径的文件名
	 * @return 如果删除成功true否则false
	 */
	public boolean delete(String strFileName) {
		File fileDelete = new File(strFileName);

		if (!fileDelete.exists() || !fileDelete.isFile()) {
			logger.debug("错误: " + strFileName + "不存在!");
			return false;
		}

		return fileDelete.delete();
	}

	/**
	 * 移动文件(只能移动文件)
	 * 
	 * @param strSourceFileName
	 *            是指定的文件全路径名
	 * @param strDestDir
	 *            移动到指定的文件夹中
	 * @return 如果成功true; 否则false
	 */
	public boolean moveFile(String strSourceFileName, String strDestDir) {
		if (copyTo(strSourceFileName, strDestDir))
			return this.delete(strSourceFileName);
		else
			return false;
	}
	
	/**
	 * 修改文件名
	 * @param filePath 需改名的文件
	 * @param definedName 自定义名称
	 * @return
	 */
	public static boolean rename(String filePath, String definedName) {
		File file = new File(filePath) ;
		if(file.exists()) {
			String fileExt = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase();
			
			String rename = file.getParent() + "/" + definedName + "." + fileExt ;
			
			if(file.renameTo(new File(rename))) {
				logger.info("修改文件名成功：{} TO {}",file.getAbsoluteFile(), rename) ;
				return true ;
			} else {
				logger.info("修改文件名失败，该文件已存在：{} TO {}",file.getAbsoluteFile(), rename) ;
				return false ;
			}
		} else {
			logger.info("该文件不存在：{}",file.getAbsoluteFile()) ;
			return false ;
		}
	}
	
	public static void main(String[] args) {
		existFile("c:/aa.txt") ;

	}

	
	/**
	 * 创建文件
	 * @param path 文件路径
	 * @return
	 */
	public static boolean existFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
				return true; 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 创建多级目录
	 * @param path 文件路径
	 * @return
	 */
	public static boolean existDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs() ;
			return true; 
		}
		return false;
	}
	
	
	/**
	 * 创建文件夹
	 * 
	 * @param strDir
	 *            要创建的文件夹名称
	 * @return 如果成功true;否则false
	 */
	public boolean makedir(String strDir) {
		File fileNew = new File(strDir);

		if (!fileNew.exists()) {
			logger.debug("文件夹不存在--创建文件夹");
			return fileNew.mkdirs();
		} else {
			logger.debug("文件夹存在");
			return true;
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param strDir
	 *            要删除的文件夹名称
	 * @return 如果成功true;否则false
	 */
	public boolean rmdir(String strDir) {
		File rmDir = new File(strDir);
		if (rmDir.isDirectory() && rmDir.exists()) {
			String[] fileList = rmDir.list();

			for (int i = 0; i < fileList.length; i++) {
				String subFile = strDir + File.separator + fileList[i];
				File tmp = new File(subFile);
				if (tmp.isFile())
					tmp.delete();
				else if (tmp.isDirectory())
					rmdir(subFile);
				else {
					logger.debug("error!");
				}
			}
			rmDir.delete();
		} else
			return false;
		return true;
	}
	
	/**
	 * 批量修改文件名称工具
     * <b>function:</b> 将指定目录下的文件的type类型的文件，进行重命名，命名后的文件将去掉type
     * <p>example: 如果type = html； index.html.html -> index.html</p>
     * <p>example: 如果type = zh_CN； index.html.zh_CN -> index.html</p>
     * <p>batchRename("F:\\server\\chat-tomcat-7.0.32\\webapps\\jwchat", "zh_CN");</p>
     * @author hoojo
     * @createDate 2012-5-16 下午02:16:48
     * @param path
     * @param type
     * @throws Exception 
     */
    public static void batchRename(String path, String type) throws Exception {
        if (path == null || "".equals(path)) {
        	throw new Exception("请输入要批量修改文件的目录！") ;
        }
        File dir = new File(path);
        File[] list = dir.listFiles();
        for (File file : list) {
            String name = file.getName();
            String[] s = name.split("\\.");
            if (s.length == 3 && type.equals(s[2])) {
                System.out.println(s[0] + "--" + s[1] + "--" + s[2]);
                file.renameTo(new File(path + "/" + s[0] + "." + s[1]));
            }
        }
    }
    
    public static void outJson(String outPath, String perfix, Object obj) {
    	try {
			//long begin3 = System.currentTimeMillis();  
			String json = JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss");
			
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outPath),"UTF-8");
	        out.write((null != perfix?perfix:"") + json);
	        out.flush();
	        out.close();
			
			//long end3 = System.currentTimeMillis();  
			//logger.info("########################文件已生成，耗时  [" + (end3 - begin3) + "毫秒]  文件路径："+outPath) ;
		} catch (IOException e) {
			e.printStackTrace();
		}   
    	
    }
	
	/**
	 * 文件下载
	 * @param fileName 文件名称(xxx.zip)
	 * @param filePath 文件路径(c:/a/b/abc.zip)
	 * @param response HttpServletResponse 对象
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void downFile(String fileName, String filePath, HttpServletResponse response)
			throws FileNotFoundException, IOException, UnsupportedEncodingException {
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		File uploadFile = new File(filePath);
		fis = new FileInputStream(uploadFile);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		bos.flush();
		fis.close();
		bis.close();
		fos.close();
		bos.close();
	}
}
