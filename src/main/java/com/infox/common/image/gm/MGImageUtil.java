package com.infox.common.image.gm;

import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.infox.common.util.StringUtil;

/**
 * IM4JAVA是同时支持ImageMagick和GraphicsMagick的。
 * 如果为true则使用GM，如果为false支持IM
 * ConvertCmdconvert = new ConvertCmd(true);
 * GraphiceMagick再window系统下需要设置环境变量
 * @author Administrator
 *
 */
@Component
public class MGImageUtil {
	
	private static Logger logger = LoggerFactory.getLogger(MGImageUtil.class);
	
	/**
	 * 默认使用GraphiceMagick处理图片
	 */
	private boolean flag = true ;
	
	/**
	 * 图片压缩
	 * @param srcPath 源图片
	 * @param newPath 新图片
	 * @param width 宽度
	 * @param height 高度
	 */
	public void compress(String srcPath, String newPath) {
		try {
			IMOperation op = new IMOperation() ;
			op.addImage(srcPath) ;
			op.crop(0, 0) ;
			op.addImage(newPath) ;
			ConvertCmd convert = new ConvertCmd(flag) ;
			convert.run(op) ;
			
			logger.info("[GraphicsMagick 处理图片]---图片压缩---源图片路径[{}]---输出路径[{}]", srcPath, newPath) ;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片缩放,根据比例
	 * @param srcPath 源图片
	 * @param newPath 新图片
	 * @param width 宽度
	 * @param height 高度
	 */
	public void resize(String srcPath, String newPath, Integer width, Integer height) {
		try {
			IMOperation op = new IMOperation() ;
			op.resize(width, height) ;
			op.addImage(srcPath) ;
			op.addImage(newPath) ;
			
			ConvertCmd convert = new ConvertCmd(flag) ;
			convert.run(op) ;
			
			logger.info("[GraphicsMagick 处理图片]---图片缩放---源图片路径[{}]---输出路径[{}]", srcPath, newPath) ;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片缩放,固定宽高
	 * 注意：在linux环境对参数需要用单引号引起来，而windows下又不能使用单引号。
	 * @param srcPath 源图片
	 * @param newPath 新图片
	 * @param width 宽度
	 * @param height 高度
	 */
	public void resize(String srcPath, String newPath, Integer width, Integer height,boolean flag) {
		try {
			IMOperation op = new IMOperation() ;
			op.resize(width, height,"!") ;
			op.addImage(srcPath) ;
			op.addImage(newPath) ;
			
			ConvertCmd convert = new ConvertCmd(flag) ;
			convert.run(op) ;
			
			logger.info("[GraphicsMagick 处理图片]---图片缩放---源图片路径[{}]---输出路径[{}]", srcPath, newPath) ;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片反色
	 * @param srcPath 源图片
	 * @param newPath 新图片
	 */
	public void negate(String srcPath, String newPath) {
		try {
			IMOperation op = new IMOperation() ;
			op.negate();
			op.addImage(srcPath) ;
			op.addImage(newPath) ;
			
			ConvertCmd convert = new ConvertCmd(flag) ;
			convert.run(op) ;
			
			logger.info("[GraphicsMagick 处理图片]---图片反色---源图片路径[{}]---输出路径[{}]", srcPath, newPath) ;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片裁剪，坐标从（top:0px left:0px）开始
	 * @param srcPath 源图片
	 * @param newPath 新图片
	 * @param width 裁剪的宽度
	 * @param height 裁剪的高度
	 */
	public void crop(String srcPath, String newPath, Integer width, Integer height) {
		try {
			IMOperation op = new IMOperation() ;
			op.crop(width, height) ;
			op.addImage(srcPath) ;
			op.addImage(newPath) ;
			
			ConvertCmd convert = new ConvertCmd(flag) ;
			convert.run(op) ;
			
			logger.info("[GraphicsMagick 处理图片]---图片裁剪，坐标从（top:0px left:0px）开始---源图片路径[{}]---输出路径[{}]", srcPath, newPath) ;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片裁剪，指定坐标(x1,y1)
	 * @param srcPath 源图片
	 * @param newPath 新图片
	 * @param width 裁剪的宽度
	 * @param height 裁剪的高度
	 * @param cutTop
	 * @param cutLeft
	 */
	public void crop(String srcPath, String newPath, Integer width, Integer height, Integer cutTop, Integer cutLeft) {
		try {
			IMOperation op = new IMOperation() ;
			op.crop(width, height, cutTop, cutLeft) ;
			op.addImage(srcPath) ;
			op.addImage(newPath) ;
			
			ConvertCmd convert = new ConvertCmd(flag) ;
			convert.run(op) ;
			
			logger.info("[GraphicsMagick 处理图片]---图片裁剪，指定坐标---源图片路径[{}]---输出路径[{}]", srcPath, newPath) ;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片裁剪，坐标从中间开始
	 * @param srcPath 源图片
	 * @param newPath 新图片
	 * @param width 裁剪的宽度
	 * @param height 裁剪的高度
	 * @param gravity 设定坐标重心（取值：
	 * 			NorthWest：左上角为坐标原点，x轴从左到右，y轴从上到下
	 * 			North：上部中间位置为坐标原点，x轴从左到右，y轴从上到下
	 * 			NorthEast：右上角位置为坐标原点，x轴从右到左，y轴从上到下
	 * 			West：左边缘中间位置为坐标原点，x轴从左到右，y轴从上到下
	 * 			Center：正中间位置为坐标原点，x轴从左到右，y轴从上到下
	 * 			East：右边缘的中间位置为坐标原点，x轴从右到左，y轴从上到下
	 * 			SouthWest：左下角为坐标原点，x轴从左到右，y轴从下到上
	 * 			South：下边缘的中间为坐标原点，x轴从左到右，y轴从下到上
	 * 			SouthEast：右下角坐标原点，x轴从右到左，y轴从下到上
	 * 			Center：正中间位置为坐标原点，x轴从左到右，y轴从上到下
	 * 	）
	 */
	public void crop(String srcPath, String newPath, Integer width, Integer height, String gravity) {
		try {
			IMOperation op = new IMOperation() ;
			op.gravity(null != gravity && !"".equals(StringUtil.replaceBlank(gravity))?gravity:"center") ;
			op.crop(width, height, 0, 0) ;
			op.addImage(srcPath) ;
			op.addImage(newPath) ;
			
			ConvertCmd convert = new ConvertCmd(flag) ;
			convert.run(op) ;
			
			logger.info("[GraphicsMagick 处理图片]---图片裁剪，坐标从中间开始---源图片路径[{}]---输出路径[{}]", srcPath, newPath) ;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String srcPath = "c:/a.jpg" ;
		String newPath1 = "c:/a1.jpg" ;
		String newPath2 = "c:/a2.jpg" ;
		String newPath3 = "c:/a3.jpg" ;
		String newPath4 = "c:/a4.jpg" ;
		String newPath5 = "c:/a5.jpg" ;
		String newPath6 = "c:/a6.jpg" ;
		String newPath7 = "c:/a7.jpg" ;
		
		MGImageUtil gm = new MGImageUtil() ;
		
		//图片压缩
		gm.compress(srcPath, newPath1) ;
		//图片缩放
		gm.resize(srcPath, newPath2, 300, 300) ;
		//图片缩放
		gm.resize(srcPath, newPath5, 500, 500, true) ;
		//图片反色
		gm.negate(srcPath, newPath3) ;
		//图片裁剪(坐标从0开始)
		gm.crop(srcPath, newPath4, 500, 500) ;
		//图片裁剪(指定坐标)
		gm.crop(newPath5, newPath6, 298, 213, 46, 349) ;
		//图片裁剪坐标从中心开始
		gm.crop(srcPath, newPath7, 400, 460, null) ;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
