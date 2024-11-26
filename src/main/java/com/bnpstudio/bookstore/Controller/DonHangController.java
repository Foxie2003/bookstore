package com.bnpstudio.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import com.bnpstudio.bookstore.exception.BadRequestException;
import com.bnpstudio.bookstore.exception.NotFoundException;

import org.springframework.http.ResponseEntity;

import com.bnpstudio.bookstore.dto.DonHangDetailDto;
import com.bnpstudio.bookstore.entity.DonHangEntity;
import com.bnpstudio.bookstore.entity.ResponseObject;
import com.bnpstudio.bookstore.service.DonHangService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/donHang")
public class DonHangController {
    @Autowired
    private DonHangService donHangService;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject<List<DonHangEntity>>> getAll() {
        return ResponseEntity.ok(
                new ResponseObject(
                        HttpStatus.OK,
                        "Get tất cả đơn hàng thành công",
                        donHangService.getAll()));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("/getByTrangThai/{trangThai}")
    public ResponseEntity<ResponseObject<List<DonHangEntity>>> getByTrangThai(@PathVariable String trangThai,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws BadRequestException {
        if (page < 0) {
            throw new BadRequestException("page number < 0");
        }
        if (size < 1) {
            throw new BadRequestException("page size < 1");
        }
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(
                new ResponseObject(
                        HttpStatus.OK,
                        "Get đơn hàng với trạng thái " + trangThai + " thành công",
                        donHangService.getDonHangByTrangThai(paging, trangThai)));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PutMapping("updateTrangThai/{id}")
    public ResponseEntity<ResponseObject<DonHangEntity>> updateTrangThai(@PathVariable int id,
            @RequestParam String trangThai) {
        return ResponseEntity.ok(
                new ResponseObject(
                        HttpStatus.OK,
                        "Update trạng thái đơn hàng có mã: " + id + " thành công",
                        donHangService.updateTrangThaiDonHang(id, trangThai)));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping({ "/getById/{id}", "/getById/" })
    public ResponseEntity<ResponseObject<DonHangDetailDto>> getById(@PathVariable(required = false) Integer id) {
        if (id == null) {
            throw new NotFoundException("id is null");
        }
        return ResponseEntity.ok(new ResponseObject(HttpStatus.OK, "Get đơn hàng có mã: " + id + " thành công",
                donHangService.getById(id)));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping("/insertDonHang")
    public ResponseEntity<ResponseObject<DonHangDetailDto>> postMethodName(@RequestBody DonHangDetailDto detailDto) {

        return ResponseEntity.ok(new ResponseObject(
                HttpStatus.OK,
                "Tạo đơn hàng thành công",
                donHangService.insertDonHang(detailDto)));
    }

}
