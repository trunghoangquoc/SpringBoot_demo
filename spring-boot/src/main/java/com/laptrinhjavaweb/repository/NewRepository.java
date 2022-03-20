package com.laptrinhjavaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laptrinhjavaweb.entity.NewEntity;

public interface NewRepository extends JpaRepository<NewEntity, Long>{
	//NewEntity  : chính là cái bảng cta đang lm vc với nó
	//Long : là kiểu dữ liệu của cái khóa chính trong bảng New(ở đây là Id)

}