package com.infocms.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class TestZIP {

	/**
	 * 功能:压缩多个文件成一个zip文件
	 * <p>
	 * 作者 陈亚标 Jul 16, 2010 10:59:40 AM
	 * 
	 * @param srcfile
	 *            ：源文件列表
	 * @param zipfile
	 *            ：压缩后的文件
	 */
	public static void zipFiles(File[] srcfile, File zipfile) {
		byte[] buf = new byte[1024];
		try {
			// ZipOutputStream类：完成文件或文件夹的压缩
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				FileInputStream in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
			System.out.println("压缩完成.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 功能:解压缩
	 * <p>
	 * 作者 陈亚标 Jul 16, 2010 12:42:20 PM
	 * 
	 * @param zipfile
	 *            ：需要解压缩的文件
	 * @param descDir
	 *            ：解压后的目标目录
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipfile, String descDir) {
		try {
			ZipFile zf = new ZipFile(zipfile);
			for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zf.getInputStream(entry);
				OutputStream out = new FileOutputStream(descDir + zipEntryName);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
				System.out.println("解压缩完成.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 功能:
	 * <p>
	 * 作者 陈亚标 Jul 16, 2010 10:58:15 AM
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 2个源文件
		File f1 = new File("D:/work/server/destiny-tomcat-7.0.42/webapps/infox/WEB-INF/buckup/201402-17182934.sql");
		File f2 = new File("D:/work/server/destiny-tomcat-7.0.42/webapps/infox/WEB-INF/buckup/20140217-183031.sql");
		File[] srcfile = { f1, f2 };
		// 压缩后的文件
		File zipfile = new File("c://biao.zip");
		TestZIP.zipFiles(srcfile, zipfile);

		// 需要解压缩的文件
		//File file = new File("D:\\workspace\\flexTest\\src\\com\\biao\\test\\biao.zip");
		// 解压后的目标目录
		//String dir = "D:\\workspace\\flexTest\\src\\com\\biao\\test\\";
		//TestZIP.unZipFiles(file, dir);
	}

}
