package com.wsh.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class GetImageAddressByImgstr
 */
@WebServlet("/GetImageAddressByImgstr")
public class GetImageAddressByImgstr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String path;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		path = request.getRealPath("/") + "upload/";//服务器中图片文件夹
		System.out.println(path);
		request.setCharacterEncoding("utf-8"); 
		response.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter(); 
		String param = request.getParameter("param");
		String imgStr2Image = imgStr2Image(param); 
		out.print(imgStr2Image);
		out.flush(); 
		out.close();
	}
	
	//如果正确， 返回 url 不正确返回 error 
	private String imgStr2Image(String imgStr) {
		// TODO Auto-generated method stub 
		if(imgStr==null){
			return "error:imgStr is null----MyImageServer"; 
			}
		File file = new File(path); 
		if (!file.exists()) {
			System.out.println("no");
			file.mkdir(); 
			}else {
				System.out.println("yes");
			}
		Date date = new Date(); 
		long time = date.getTime();
		String strName = time+".jpg";
		String imgPath = path+strName;
		String imgUrl = "http://192.168.1.4:8080/Mr_Right/upload/"+strName; //这里的192.168.1.4为以太网的ip地址，与安卓端不一样
		try { 
			byte[] bs = new BASE64Decoder().decodeBuffer(imgStr); 
			for (int i = 0; i < bs.length; i++) {
				if (bs[i] < 0) {
					bs[i] += 256;
					} 
				} 
			OutputStream out = new FileOutputStream(imgPath); 
			out.write(bs); 
			out.flush(); 
			out.close(); 
			} catch (IOException e) {
				e.printStackTrace(); 
				return "error:IOException----MyImageServer"; 
				} 
		return imgUrl;
		}

}
