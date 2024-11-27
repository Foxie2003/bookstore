package com.bnpstudio.bookstore.controller;

import org.springframework.http.ResponseEntity;

import com.bnpstudio.bookstore.dto.PageResponse;
import com.bnpstudio.bookstore.dto.SachDetailDto;
import org.springframework.data.domain.PageRequest;
import com.bnpstudio.bookstore.service.SachService;

import jakarta.validation.Valid;

import com.bnpstudio.bookstore.entity.ResponseObject;
import com.bnpstudio.bookstore.exception.NotFoundException;
import com.bnpstudio.bookstore.exception.BadRequestException;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/sach")
public class SachController {
    @Autowired
    private SachService sachService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping("/getAll")
    public <T> ResponseEntity<ResponseObject<List<SachDetailDto>>> getAll() {
        return ResponseEntity.ok(
                new ResponseObject(
                        HttpStatus.OK,
                        "Get tất cả lĩnh vực thành công",
                        sachService.getAll()));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping({ "/getById/{id}", "/getById/" })
    public ResponseEntity<ResponseObject<SachDetailDto>> getById(@PathVariable(required = false) Integer id) {
        if (id == null) {
            throw new BadRequestException("id is null");
        }
        if (id < 1) {
            throw new BadRequestException("id < 1");
        }
        SachDetailDto sachDetailDto = sachService.getById(id);
        return ResponseEntity.ok(new ResponseObject(HttpStatus.OK, "Tìm thấy sách", sachDetailDto));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("/getByName/{name}")
    ResponseEntity<ResponseObject<List<SachDetailDto>>> getByName(@PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<SachDetailDto> sachs = sachService.findByName(pageable, name);
        if (sachs.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK, "query product succsessfully", sachs));
        } else {
            throw new NotFoundException("cannot found product with name= " + name);
        }
    }

    // @PostMapping("/getByNameAndTacGia")
    // List<SachDetailDto> getByNameAndTacGia(@RequestBody SachDetailDto sach) {
    // System.out.println("phg:" + sach.getTenSach());
    // try {
    // List<SachDetailDto> sachs = sachService.findProduct(sach);
    // return sachs;

    // } catch (Exception e) {
    // throw e;
    // }
    // }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject<SachDetailDto>> insertProduct(
            @Valid @RequestBody SachDetailDto sachDetailDto) {
        try {

            SachDetailDto result = sachService.insertProduct(sachDetailDto);
            return ResponseEntity.ok(
                    new ResponseObject<>(HttpStatus.OK, "Thêm sách thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseObject<SachDetailDto>> updateProduct(
            @Valid @RequestBody SachDetailDto sachDetailDto) {
        try {
            SachDetailDto result = sachService.updateProduct(sachDetailDto);
            return ResponseEntity.ok(
                    new ResponseObject<>(HttpStatus.OK, "Cập nhật sách thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseObject<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject<String>> deleteSach(@PathVariable Integer id) {
        try {
            sachService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Xóa sách thành công", "xóa thành công"));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND, e.getMessage(), "lỗi"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi xóa sách: " + e.getMessage(),
                            "lỗi"));
        }
    }

    @GetMapping("/getSachPaging")
    public ResponseEntity<ResponseObject<PageResponse<SachDetailDto>>> getAllPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Validate size
            if (size <= 0) {
                return ResponseEntity.badRequest()
                        .body(new ResponseObject<>(
                                HttpStatus.BAD_REQUEST,
                                "Kích thước trang phải lớn hơn 0",
                                null));
            }
            
            // Validate page
            if (page < 0) {
                return ResponseEntity.badRequest()
                        .body(new ResponseObject<>(
                                HttpStatus.BAD_REQUEST,
                                "Số trang không được âm",
                                null));
            }
            
            Pageable paging = PageRequest.of(page, size);
            PageResponse<SachDetailDto> sachPage = sachService.getAllPaging(paging);

            return ResponseEntity.ok(new ResponseObject<>(
                    HttpStatus.OK,
                    "Lấy danh sách sách thành công",
                    sachPage));
                    
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject<>(
                            HttpStatus.NOT_FOUND,
                            e.getMessage(),
                            null));
        } catch (Exception e) {
            e.printStackTrace(); // Thêm dòng này để debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject<>(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Lỗi khi lấy danh sách sách: " + e.getMessage(),
                            null));
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("/getSachByIdDanhMuc/{idDanhMuc}")
    public ResponseEntity<ResponseObject<List<SachDetailDto>>> getSachPagingbyDanhMuc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Integer idDanhMuc) {
        try {
            Pageable paging = PageRequest.of(page, size);
            PageResponse<SachDetailDto> sachPage = sachService.getSachByDanhMucPaging(paging,idDanhMuc);

            return ResponseEntity.ok(new ResponseObject(
                    HttpStatus.OK,
                    "Lấy danh sách sách thành công",
                    sachPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Lỗi khi lấy danh sách sách: " + e.getMessage(),
                            null));
        }
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("/getSachByIdLinhVuc/{idLinhVuc}")
    public ResponseEntity<ResponseObject<List<SachDetailDto>>> getSachPagingbyLinhVuc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Integer idLinhVuc) {
        try {
            Pageable paging = PageRequest.of(page, size);
            PageResponse<SachDetailDto> sachPage = sachService.getSachByLinhVucPaging(paging,idLinhVuc);

            return ResponseEntity.ok(new ResponseObject(
                    HttpStatus.OK,
                    "Lấy danh sách sách thành công",
                    sachPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Lỗi khi lấy danh sách sách: " + e.getMessage(),
                            null));
        }
    }

}
