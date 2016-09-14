package com.colecciones;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



public class Sets {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List <String>setH = new LinkedList<String>();
		setH.add("zucurio");
		setH.add("pepa");
		setH.add("Moca");
		setH.add("pipa");
		setH.add("pepa");
		setH.add(null);
		setH.add("brandoni");
		setH.add("chacal");
		
		//setH.add(1);
		//setH.add(2);
		
		for (String a : setH) {
			System.out.println(a!=null?a.toString():"");
		}
		

	}

}
