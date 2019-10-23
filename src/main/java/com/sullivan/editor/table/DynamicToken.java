package com.sullivan.editor.table;

import java.util.ArrayList;
import java.util.List;

public class DynamicToken {
	private int id;
	private List<Integer> lines = new ArrayList<>();
	private String symbol;
	private int category;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getLines() {
		return lines;
	}
	public void setLines(List<Integer> lines) {
		this.lines = lines;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
}