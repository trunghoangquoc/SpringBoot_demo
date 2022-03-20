package com.laptrinhjavaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.laptrinhjavaweb.converter.NewConverter;
import com.laptrinhjavaweb.dto.NewDTO;
import com.laptrinhjavaweb.entity.CategoryEntity;
import com.laptrinhjavaweb.entity.NewEntity;
import com.laptrinhjavaweb.repository.CategoryRepository;
import com.laptrinhjavaweb.repository.NewRepository;
import com.laptrinhjavaweb.service.INewService;

@Service
public class NewService implements INewService {

	@Autowired
	private NewRepository newRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private NewConverter newConverter;

	// list NewDTO có phân trang
	@Override
	public List<NewDTO> findAll(Pageable pageable) {
		// B1.tạo 1 List DTO để hứng data lấy từ database
		List<NewDTO> results = new ArrayList<>();
		
		//để truyền dc cái pageable xuống entity để mà lấy danh sách lên
		//B2.tạo list newentity lấy data từ database lên
		List<NewEntity> entities = newRepository.findAll(pageable).getContent();
		
		//converter từ entity sang DTO rồi thêm vào 	results.add(newDTO);
		for (NewEntity item : entities) {
			NewDTO newDTO = newConverter.toDTO(item);
			results.add(newDTO);
		}
		return results;
	}

	// list NewDTO ko phân trang
	@Override
	public List<NewDTO> findAll() { 
		List<NewDTO> results = new ArrayList<>();
		// để truyền dc cái pageable xuống entity để mà lấy danh sách lên
		List<NewEntity> entities = newRepository.findAll();
		for (NewEntity item : entities) {
			NewDTO newDTO = newConverter.toDTO(item);// converter cái entity vừa lấy dưới database sang dto
			results.add(newDTO);
		}
		return results;
	}

	@Override
	public int totalItem() {
		return (int) newRepository.count();
	}

	/*B1.khi insert hay update thì ấn submit ở client thì sẽ gửi về server
	   cho thằng DTO nhận.
	-converter từ DTO sang entity để lưu xuống database
	B2.converter từ thằng entity sang DtO để đưa về controller để đưa ra view
	*/
	@Override
	public NewDTO save(NewDTO newDTO) {
		NewEntity newEntity = new NewEntity();
		if (newDTO.getId() != null) {
//khi findOne(dto.getId()); sẽ lấy lên dữ liệu của các column trong table new theo Id
			NewEntity oldNewEntity = newRepository.findOne(newDTO.getId());
			newEntity = newConverter.toEntity(newDTO, oldNewEntity);
		} else {
			newEntity = newConverter.toEntity(newDTO);
		}

		CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
		newEntity.setCategory(categoryEntity);
		newEntity = newRepository.save(newEntity);
		return newConverter.toDTO(newEntity);
	}

	@Override
	public void delete(long[] ids) {
		for (long item : ids) {
			newRepository.delete(item);
		}
	}
	/*
	 * @Override
	 * 
	 * @Transactional NewDTO insert(NewDTO newDto) { CategoryEntity category =
	 * categoryRepository.findOneByCode(newDto.getCategoryCode()); NewEntity
	 * newEntity = newConverter.toEntity(newDto); newEntity.setCategory(category);
	 * //lưu vào entity rồi đổ vào hàm save //hàm save đc thằng Repository viết sẵn
	 * rồi
	 * 
	 * return newConverter.toDto(newRepository.save(newEntity)); //lưu xuống
	 * database rồi lấy lên converter ngươc lại Dto }
	 * 
	 * @Override Cách 1 :
	 * 
	 * @Transactional public NewDTO update(NewDTO dto) { NewEntity oldNew =
	 * newRepository.findOne(dto.getId());
	 */// khi findOne(dto.getId()); sẽ lấy lên dữ liệu của các column trong table new
		// theo Id
	/*
	 * CategoryEntity category =
	 * categoryRepository.findOneByCode(dto.getCategoryCode());
	 * oldNew.setCategory(category); NewEntity updateNew =
	 * newConverter.toEntity(oldNew,dto); return
	 * newConverter.toDto(newRepository.save(updateNew)); }
	 * 
	 * Cách 2 : * public NewDTO update(NewDTO dto) { NewEntity oldNew =
	 * newRepository.findOne(dto.getId());
	 */// khi findOne(dto.getId()); sẽ lấy lên dữ liệu của các column trong table new
		// theo Id
	/*
	 * NewEntity updateNew = newConverter.toEntity(oldNew,dto); CategoryEntity
	 * category = categoryRepository.findOneByCode(dto.getCategoryCode());
	 * updateNew.setCategory(category); return
	 * newConverter.toDto(newRepository.save(updateNew)); }
	 */

}
