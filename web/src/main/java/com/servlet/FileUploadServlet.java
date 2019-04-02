package com.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

public class FileUploadServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public FileUploadServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// use fileupload.jar handle the file upload
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		// 1.1 设置是否生产临时文件临界值。大于2M生产临时文件。保证：上传数据完整性。
		fileItemFactory.setSizeThreshold(1024 * 1024 * 2); // 2MB
		// 1.2 设置临时文件存放位置
		// * 临时文件扩展名 *.tmp ，临时文件可以任意删除。
		String tempDir = this.getServletContext().getRealPath("/temp");
		fileItemFactory.setRepository(new File(tempDir));
		ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
		// 2.1 如果使用无参构造 ServletFileUpload() ，手动设置工厂
		// servletFileUpload.setFileItemFactory(fileItemFactory);
		// 2.2 单个上传文件大小
		// servletFileUpload.setFileSizeMax(1024*1024 * 2); //2M
		// 2.3 整个上传文件总大小
		// servletFileUpload.setSizeMax(1024*1024*10); //10M
		// 2.4 设置上传文件名的乱码
		// * 首先使用 setHeaderEncoding 设置编码
		// * 如果没有设置将使用请求编码 request.setCharacterEncoding("UTF-8")
		// * 以上都没有设置，将使用平台默认编码
		// servletFileUpload.setHeaderEncoding("UTF-8");
		servletFileUpload.setProgressListener(new MyProgressListener());
		try
		{
			// 解析request ,List存放 FileItem （表单元素的封装对象，一个<input>对应一个对象
			List<FileItem> fileList = servletFileUpload.parseRequest(request);
			for (FileItem item : fileList)
			{
				if (item.isFormField())
				{
					String fieldName = item.getFieldName();
					// System.out.println(fieldName);
					String fieldValue = item.getString();
					// System.out.println(fieldValue);
				} else
				{
					String parentDir = this.getServletContext().getRealPath("/uploadfiles");
					// System.out.println(parentDir);
					String name = item.getName();
					FileUtils.copyInputStreamToFile(item.getInputStream(), new File(parentDir, name));
					item.delete();
				}

			}

		} catch (FileUploadException e)
		{
			e.printStackTrace();
		}

		response.getWriter().write("Upload success!");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		doGet(request, response);
	}

}

class MyProgressListener implements ProgressListener
{

	@Override
	public void update(long pBytesRead, long pContentLength, int pItems)
	{
		long notReadBytes = pContentLength - pBytesRead;
		double readPrecent = pBytesRead * 100 / pContentLength;
		// System.out.println(readPrecent + "%");
	}

}
