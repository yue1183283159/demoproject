package com.component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	private String dirPath = "E:/ssm-upload";// 定义本地磁盘路径
	private String url = "http://image.jt.com";// 定义url访问路径
	// 图片上传编程思路：
	// 1. 获取图片的名称
	// 2. 截取图片的类型
	// 3. 判断是否为图片格式
	// 4. 判断是否是恶意程序
	// 5. 定义磁盘访问路径和url访问路径
	// 6. 准备以时间为界限的文件夹
	// 7. 让图片不重名，时间毫秒数+三位数据数
	// 8. 创建文件夹，文件写入磁盘
	// 9. 返回数据给客户端
	// 真实路径: E:/jt-upload/2018/1/17/12/123.jpg
	// 虚拟路径: http://image.jt.com/2018/1/17/12/123.jpg
	
	public void uploadFile(MultipartFile uploadFile)
	{
		//PicUploadResult result = new PicUploadResult();
		String fileName=uploadFile.getOriginalFilename();
		String fileType=fileName.substring(fileName.lastIndexOf("."));
		if(!fileType.matches("^.*(jpg|png|gif)$")) 
		{
			//result.setError(1);
			//return result;
		}
		
		try
		{
			//4.判断是否是恶意程序
			//判断一个文件是否为图片，一般获取图片的高度和宽度，如果二者都不为0表示文件为图片
			BufferedImage bufferedImage=ImageIO.read(uploadFile.getInputStream());
			int width=bufferedImage.getWidth();
			int height=bufferedImage.getHeight();
			if(width==0||height==0) 
			{
				//result.setError(1);
				//return result; 
			}
			
			//准备时间文件夹，以时间格式yyyy/MM/dd/hh
			String datePathDir=new SimpleDateFormat("yyyy/MM/dd/HH").format(new Date());
			//7.准备时间 时间毫秒数+三位随机数
			String millis=System.currentTimeMillis()+"";
			Random random=new Random();
			int randomNum=random.nextInt(999);//0-999的随机数
			String randomPath=millis+randomNum;
			
			//8.创建文件夹 E:/jt-upload/yyyy/MM/dd/HH
			String localPath=dirPath+"/"+datePathDir;
			File file=new File(localPath);
			if(!file.exists()) 
			{
				file.mkdirs();
			}
			
			//8 通过工具类实现写盘操作
			//E:/jt-upload/yyyy/MM/dd/HH/232323111ll.jpg
			String localPathFile=localPath+"/"+randomPath+fileType;
			uploadFile.transferTo(new File(localPathFile));
			
			//result.setHeight(heighy+"");
			//result.setWidth(width+"");
			String urlPath=url+datePathDir+"/"+randomPath+fileType;
			//result.setUrl(urlPath);
			
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		
	}
}
