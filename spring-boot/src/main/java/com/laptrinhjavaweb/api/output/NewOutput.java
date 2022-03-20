package com.laptrinhjavaweb.api.output;

import java.util.ArrayList;
import java.util.List;

import com.laptrinhjavaweb.dto.NewDTO;

public class NewOutput {
	/* 2.  server trả ra  client sau khi xử lý:
		   -totalPage
		   -page
		   -list<data(newDTO, userDTO, commentDTO,....)>   */
	private int page;
	private int totalPage;
	private List<NewDTO> listResult = new ArrayList<>();
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<NewDTO> getListResult() {
		return listResult;
	}

	public void setListResult(List<NewDTO> listResult) {
		this.listResult = listResult;
	}
}
