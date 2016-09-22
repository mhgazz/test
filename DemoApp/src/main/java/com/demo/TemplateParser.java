package com.demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * renders the JS templating
 * contains a privete instance of the Nashorn mediator
 * in order to preserve the request javascript context
 * @author mariano
 *
 */

public class TemplateParser {

	private static final Logger logger =
			LoggerFactory.getLogger(TemplateParser.class);
	
	private NashornMed nashorn = new NashornMed();

	private String templatePath ;
	
	public TemplateParser(String templatePath) {
		this.templatePath = templatePath;
	}
	
	/**
	 * template rendering method
	 * @param template
	 * @param bindings
	 * @return
	 */
	public String renderer (String template, HashMap bindings) {
		
		logger.debug("Rendering template: " + template);
		if (bindings!=null && !bindings.isEmpty()) 
			nashorn.setVariables(bindings);
		
		Object obj=null;
		StringBuffer bufMain = null;
		try {
			//read the physical template file
			FileReader rd = new FileReader(templatePath + template);
			BufferedReader br = new BufferedReader(rd); 
			String s;
			
			/**
			 * this code block makes the parsing itself
			 */
			StringBuffer bufScpt = new StringBuffer();
			bufMain = new StringBuffer();
			boolean isScript = false;
			String script = "";
			String exprs = "";
			while((s = br.readLine()) != null) { 
				if (s.indexOf("<script type=\"server/javascript\">")>0) {
					bufScpt = new StringBuffer();
					isScript=true;
					script="";
					continue;
				}
				if (s.indexOf("</script")>0) {
					isScript=false;
					script = bufScpt.toString();
					nashorn.getValue(script);
					bufScpt = new StringBuffer();
					continue;
				}
				if (s.indexOf("data-if=")>0) {
					s=parseTagIf(s);
					bufMain.append(s);
					continue;
				}
				if (s.indexOf("data-for-")>0) {
					s=parseTagFor(s);
					bufMain.append(s);
					continue;
				}
				if (s.indexOf("${")>0) {
					bufMain.append(parseVariable(s));
					continue;
				}
				if (isScript) bufScpt.append(s);
				else bufMain.append(s);
			} 
			rd.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufMain.toString();
	}

	/**
	 * this method is encharged of parsing the 'data-for' attributes
	 * @param s
	 * @return
	 */
	private String parseTagFor(String s) {
		int indSt = s.indexOf("data-for-");
		int indEn = s.indexOf("\"",indSt+9);
		String var = s.substring(indSt+9, indEn-1);
		int colEn = s.indexOf("\"",indEn+2);
		String col = s.substring(indEn+1, colEn);
		StringBuffer bf = new StringBuffer();
		
		String arrB = (String) nashorn.getValue(col+".toString()");
		String [] arrElemts = arrB.substring(1, arrB.length()-1).split(",");
		int tagSt = s.indexOf("${"+var+"}",indEn+2);
		int tagEn = tagSt+2+var.length()+1;
		
		for (int arrInd=0;arrInd<arrElemts.length;arrInd++) {
			StringBuffer line = new StringBuffer();
			line.append(s.substring(0, indSt-1));
			line.append(s.substring(colEn+1, tagSt));
			line.append(arrElemts[arrInd]);
			line.append(s.substring(tagEn, s.length()));
			bf.append(line.toString());
		}
		
		return bf.toString();
	}

	/**
	 * encharged of parsing 'data-if' attributes
	 * @param s
	 * @return
	 */
	private String parseTagIf(String s) {
		
		int indSt = s.indexOf("data-if=\"");
		int indEn = s.indexOf("\"",indSt+9);
		String var = s.substring(indSt+9, indEn);
		if (nashorn.getValue(var)==null 
				|| nashorn.getValue(var).toString().equals("false"))
			return "";
		String stPp = s.subSequence(0, indSt-1).toString();
		String stUp = s.subSequence(indEn+1, s.length()).toString();
		s = stPp + stUp;
		s = parseVariable(s);
		return s;
	}
	
	private String parseVariable(String s) {
		
		StringBuffer bufExpr = null;
		int index = 0;
		while (index<s.length()) {
			int varSt = s.indexOf("${",index);
			if (varSt==-1) break;
			bufExpr = new StringBuffer();
			int varEn = s.indexOf("}",varSt);
			String exprs = s.substring(varSt+2, varEn);
			String val = nashorn.getValue(exprs);
			bufExpr.append( s.substring(0, varSt));
			bufExpr.append(val);
			bufExpr.append(s.substring(varEn+1, s.length()));
			index=index+exprs.length();
			s = bufExpr.toString();
		}
		return bufExpr.toString();
	}
}
