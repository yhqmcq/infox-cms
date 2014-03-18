package com.infox.sysmgr.web.form;

import java.io.File;
import java.util.Comparator;
import java.util.Locale;

import com.infox.common.web.page.EasyuiTree;

public class FileWrapForm extends EasyuiTree<FileWrapForm> {
	
	/** 文件名称 */
	private String fileName ;
	
	/** 文件大小 */
	private String fileSize ;
	
	/** 最后修改时间 */
	private String lastModified ;
	
	/** 目录名称 */
	private String dirName ;
	
	/** 文件路径 */
	private String path ;
	
	/** 是否目录 */
	private boolean isDir = false ;
	
	/** 是否可编辑 */
	private boolean editable ;
	
	/** 文件图标 */
	private String iconCls ;
	
	/** 原文件名 */
	private String origName ;
	
	/** 修改的文件名*/
	private String destName ;
	
	public String getOrigName() {
		return origName;
	}

	public void setOrigName(String origName) {
		this.origName = origName;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isDir() {
		return isDir;
	}

	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	
	/**
	 * 可编辑的后缀名
	 */
	public static final String[] EDITABLE_EXT = new String[] { "html", "htm", "css", "js", "txt" };
	
	/**
	 * 是否允许编辑
	 * @param ext 文件的扩展名
	 * @return
	 */
	public static boolean editableExt(String ext) {
		ext = ext.toLowerCase(Locale.ENGLISH);
		for (String s : EDITABLE_EXT) {
			if (s.equals(ext)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获得文件的图标名称
	 * <ul>
	 * <li>directory = folder</li>
	 * <li>jpg,jpeg = jpg</li>
	 * <li>gif = gif</li>
	 * <li>html,htm = html</li>
	 * <li>swf = swf</li>
	 * <li>txt = txt</li>
	 * <li>其他 = unknow</li>
	 * </ul>
	 * 
	 * @return
	 */
	public static String getIco(String ext) {
		if (ext.equals("jpg") || ext.equals("jpeg")) {
			return "jpg";
		} else if (ext.equals("png")) {
			return "png";
		} else if (ext.equals("gif")) {
			return "gif";
		} else if (ext.equals("html") || ext.equals("htm")) {
			return "html";
		} else if (ext.equals("swf")) {
			return "swf";
		} else if (ext.equals("txt")) {
			return "txt";
		} else if (ext.equals("sql")) {
			return "sql";
		}else {
			return "unknow";
		}
	}

	/**
	 * 文件比较器，文件夹靠前排。
	 */
	public static class FileComparator implements Comparator<File> {
		public int compare(File o1, File o2) {
			if (o1.isDirectory() && !o2.isDirectory()) {
				return -1;
			} else if (!o1.isDirectory() && o2.isDirectory()) {
				return 1;
			} else {
				return o1.compareTo(o2);
			}
		}
	}
	
	/**
	 * 文件比较器，文件越大越前。
	 */
	public static class FileLengthComparator implements Comparator<File> {
		public int compare(File o1, File o2) {
			if (o1.length() > o2.length()) {
				return -1;
			} else if (o1.length() < o2.length()) {
				return 1;
			} else {
				return o1.compareTo(o2);
			}
		}
	}
}
