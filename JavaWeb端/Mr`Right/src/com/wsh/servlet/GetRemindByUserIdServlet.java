package com.wsh.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.bean.Remind;
import com.wsh.service.RemindService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class GetRemindByUserIdServlet
 */
@WebServlet("/GetRemindByUserIdServlet")
public class GetRemindByUserIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRemindByUserIdServlet() {
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String userid= request.getParameter("userid");
		
		RemindService remindService = new RemindService();
		List<Remind> reminds = new ArrayList<>();
		reminds = remindService.getRemindByUserId(Integer.parseInt(userid));
		
		JsonConfig jsonConfig = new JsonConfig();
		String remindsToJson = JSONArray.fromObject(reminds.toArray(),jsonConfig).toString();
		response.getWriter().write(remindsToJson);
	}

}
