package com.cb.boredproject.menus;


public class MenuGenerator{

	private String[] list;

	public MenuGenerator(int menuSize){

		list = new String[menuSize];
		generate(menuSize);

	}


	private void generate(int menuSize){

		for(int i = 0; i < menuSize; i++){

			list[i] = (i+1) + ". ";

		}
	}

	public String[] getList(){
		return list;
	}
}