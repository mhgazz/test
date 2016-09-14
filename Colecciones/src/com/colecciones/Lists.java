package com.colecciones;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//esto es una prueba para comprobar si funciona Github

public class Lists {

	static List<String> listA = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		listA = new LinkedList<String>();
		listA.add("pepa");
		listA.add("Moca");
		listA.add("pipa");
		listA.add("chicha");
		listA.add("cachufleta");
		listA.add("mondongo");
		listA.add("piroba");
		listA.add("rancul");
		listA.add("sandia");
		listA.add(1,"tango");
		
		for (String a : listA) {
			System.out.println(a.toString());
		}
	}

}
