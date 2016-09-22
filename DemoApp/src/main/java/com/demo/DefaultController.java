package com.demo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * default Controller class for the application 
 * @author mariano
 */

@Controller
public class DefaultController {

	
	private static final Logger logger =
			LoggerFactory.getLogger(DefaultController.class);
	
	@Autowired
	ServletContext servletContext;

	
	/**
	 * default method
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		logger.debug("Default controller");
		model.addAttribute("message", "Demo application");
		return "index";

	}

	/**
	 * this method maps the requests for rendering the JS templates
	 * also passes the querystring parameter in the variable maps
	 * @param tempname
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/template/{tempname:.+}", method = RequestMethod.GET)
	@ResponseBody
	public String renderTemplate(@PathVariable("tempname") String tempname,HttpServletRequest request) {
		logger.debug("Default controller - template rendering: " + tempname);
		
		//map object for passing request variables and other values
		Map parammap = request.getParameterMap();
		String id = "";
		if (parammap.get("id")==null || parammap.get("id").toString().equals("")) {
			id = "1";
		}
		else {
			id = request.getParameter("id");
			if (id==null || id.equals(""))
				id = "1";
		}
		HashMap<String, String> varMap = new HashMap<String, String>();
		varMap.put("id", id);
		
		//set the context
		String resourcetPath = request.getContextPath();
		varMap.put("resourcetPath",resourcetPath+"/resources/core");
		String templeatePath = servletContext.getRealPath("/") + "/resources/templating/";
		
		/**
		 * a new parser instence is create for each request
		 * provided it contains a privete nashorn mediator instance
		 * the context is just valid for the current request
		 */
		TemplateParser parser = new TemplateParser(templeatePath);
		String output = parser.renderer(tempname + ".tpl",varMap);
		return output;

	}
	

}