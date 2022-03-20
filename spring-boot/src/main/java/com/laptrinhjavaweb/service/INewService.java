package com.laptrinhjavaweb.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.laptrinhjavaweb.dto.NewDTO;

public interface INewService {
  NewDTO save(NewDTO newDTO );
//save = insert + update
	// 2 hàm thêm mới và update
	/*
	 * NewDTO insert (NewDTO newDto); 
	 * NewDTO update (NewDTO updateNew);
	 */
  
  void delete(long[] ids);
  
  /* 2.  server trả ra  client sau khi xử lý:
   -totalPage
   -page
   -list<data(newDTO, userDTO, commentDTO,....)>   
   */
  List<NewDTO> findAll(Pageable pageable);//trả về list NewDTO có phân trang
  List<NewDTO> findAll();//chỉ trả về list newDTO ko có phân trang
	int totalItem();
}
