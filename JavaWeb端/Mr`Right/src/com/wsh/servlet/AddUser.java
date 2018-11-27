package com.wsh.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.bean.User;
import com.wsh.service.UserService;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		request.setCharacterEncoding("utf-8"); 
		response.setCharacterEncoding("utf-8"); 
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String headimage = request.getParameter("headimage");
		String phonenumber = request.getParameter("phonenumber");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String sign = request.getParameter("sign");
		
		System.out.println(username+" "+password+" "+headimage+" "+phonenumber+" "+sex+" "+birthday+" "+sign);
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setHeadimage(headimage);
		user.setBirthday(birthday);
		user.setPhonenumber(phonenumber);
		user.setSex(sex);
		user.setSign(sign);
		
		UserService userService = new UserService();
		int n = userService.register(user);
		response.getWriter().write(n+"");
	}

}
