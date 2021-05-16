package com.example.restwithspringboot;

import java.util.ArrayList;

public class Inventory {

	private static int id = 0;
	private String name;
	private int products_amount;

	ArrayList<Product> products_list;

	public Inventory(String name, int products_amount) {
		this.name = name;
		this.products_amount = products_amount;
		products_list = new ArrayList<Product>();

	}

	public void add_product(Product p) {
		products_list.add(p);
	}

	public Product search_product_by_id(long id) {
		for (int i = 0; i < products_list.size(); i++) {
			if (products_list.get(i).getProductId() == id) {
				return products_list.get(i);
			}
		}
		return null;
	}

}
