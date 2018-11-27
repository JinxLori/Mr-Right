package com.wsh.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsh.bean.Post;
import com.wsh.bean.Topic;
import com.wsh.service.PostService;
import com.wsh.service.TopicService;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class GetTopicRand
 */
@WebServlet("/GetTopicRand")
public class GetTopicRand extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTopicRand() {
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

		TopicService topicService = new TopicService();
		List<Topic> topics = new ArrayList<>();
		topics = topicService.getTopicRand();
		
		JsonConfig jsonConfig = new JsonConfig();
		String postsToJson = JSONArray.fromObject(topics.toArray(),jsonConfig).toString();
		response.getWriter().write(postsToJson);
	}

}
