package com.laptrinhjavaweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptrinhjavaweb.api.output.NewOutput;
import com.laptrinhjavaweb.dto.NewDTO;
import com.laptrinhjavaweb.service.INewService;


@CrossOrigin
@RestController
public class NewAPI {

	@Autowired
	private INewService newService;
	
	//@PathVariable là annotation ĐỂ LẤY THAM SỐ là một thành phần của đường dẫn 
	//đc gửi từ client về server để lấy data từ database theo cái THAM Số đấy.
//		 
//		        Ví dụ http://acme.com/book/5 thì 5 là thành phần dùng làm tham số
//				@GetMapping("/book/{id}")
//	              public String getBookByPath(@PathVariable("id") int id) {
//	                        return "Book id = " + id;
//	                       }
		
	//nếu dùng @RequestParam là annotation ĐỂ LẤY THAM SỐ là biến trong query string.
	//thì url sẽ là http://acme.com/book/?id=5
	
	/*Phân Trang
 1. client gửi về server   : 
     +page
	 +limit (cái limit là động tức là số lượng item hiển thị trong mỗi page là tùy theo ý người dùng.)
 
 2.  server trả ra  client sau khi xử lý:
   -totalPage
   -page
   -list<data(newDTO, userDTO, commentDTO,....)>   */
	@GetMapping(value = "/new")
	public NewOutput showNew(@RequestParam(value = "page", required = false) Integer page,
			                 @RequestParam(value = "limit", required = false) Integer limit) {
		
/*Cách 1 :
* required = false : tránh trường hợp khi hiển thị danh sách ko muốn phân trang 
		tức là trên url ko có @RequestParam là page và limit
		
* Cách 2 : 
* dùng defaultValue <=> required

 */
		NewOutput result = new NewOutput();
		if (page != null && limit != null) {
			result.setPage(page);
			Pageable pageable = new PageRequest(page - 1, limit);
			//hiển thị danh sách lên có phân trang
			result.setListResult(newService.findAll(pageable));
			result.setTotalPage((int) Math.ceil((double) (newService.totalItem()) / limit));
		} else {
			//hiển thị danh sách lên mà ko có phân trang
			result.setListResult(newService.findAll());
		}
		return result;
	}
	
	@PostMapping(value = "/new")
	public NewDTO createNew(@RequestBody NewDTO model) {
		return newService.save(model);
	}
	
	
	@PutMapping(value = "/new/{id}")
	public NewDTO updateNew(@RequestBody NewDTO model, @PathVariable("id") long id) {
		model.setId(id);
		return newService.save(model);
	}
	
	@DeleteMapping(value = "/new")
	public void deleteNew(@RequestBody long[] ids) {
		newService.delete(ids);
	}
}
//Test 
//@PostMapping ("/news")
//public String test() {
//
//        return "ss";
//}
/*@RestController = @Controller + @ResponseBody
 * @PostMapping = @RequestMapping + RequestMethod.POST
 * @ResponseBody như ModelAndView để đưa ra view sau khi xử lý xong
*/

//@Controller
//public class NewAPI {
//
//        @RequestMapping(value = "/new", method = RequestMethod.POST)
//        @ResponseBody // khi server trả data về client thì cũng phải convert qua kiểu json
//        
//        public NewDTO createNew(@RequestBody NewDTO model) {
//                return model;
//
//        }
//
//}