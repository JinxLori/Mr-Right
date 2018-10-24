package com.wsh.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.service.CommentService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class GetCommentNiceUids
 */
@WebServlet("/GetCommentNiceUids")
public class GetCommentNiceUids extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCommentNiceUids() {
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
		
		String comment_id = request.getParameter("comment_id");
		List<String> uids = new ArrayList<String>();
		CommentService commentService = new CommentService();
		uids = commentService.getCommentNiceUids(Integer.parseInt(comment_id));
		
		JsonConfig jsonConfig = new JsonConfig();
		String uidsToJson = JSONArray.fromObject(uids.toArray(),jsonConfig).toString();
		response.getWriter().write(uidsToJson);
	}

}
