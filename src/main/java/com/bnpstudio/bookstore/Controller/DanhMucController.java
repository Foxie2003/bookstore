package com.bnpstudio.bookstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnpstudio.bookstore.dto.DanhMucDto;
import com.bnpstudio.bookstore.entity.ResponseObject;
import com.bnpstudio.bookstore.service.DanhMucService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/danhMuc")
public class DanhMucController {
    @Autowired
    private DanhMucService danhMucService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject<List<DanhMucDto>>> getAll() {
        return ResponseEntity.ok(
                new ResponseObject<List<DanhMucDto>>(
                        HttpStatus.OK,
                        "Get tất cả các danh mục thành công",
                        danhMucService.getAll()));
    }

    @GetMapping("/getByIdLinhVuc/{idLinhVuc}")
    public ResponseEntity<ResponseObject<List<DanhMucDto>>> getByIdLinhVuc(@PathVariable int idLinhVuc) {
        return ResponseEntity.ok(
                new ResponseObject<List<DanhMucDto>>(
                        HttpStatus.OK,
                        "Get các danh mục có id lĩnh vực = " + idLinhVuc + " thành công",
                        danhMucService.getByIdLinhVuc(idLinhVuc)));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ResponseObject<DanhMucDto>> getById(@PathVariable int id) {
        return ResponseEntity.ok(
                new ResponseObject<>(
                        HttpStatus.OK,
                        "Get danh mục có id = " + id + " thành công",
                        danhMucService.getById(id)));
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject<DanhMucDto>> insertDanhMuc(@RequestBody DanhMucDto danhMuc) {
        return ResponseEntity.ok(
            new ResponseObject<DanhMucDto>(
                HttpStatus.OK,
                "Thêm danh mục thành công",
                danhMucService.insertDanhMuc(danhMuc)
            )
        );
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseObject<DanhMucDto>> updateDanhMuc(@RequestBody DanhMucDto danhMuc) {
        return ResponseEntity.ok(
            new ResponseObject<DanhMucDto>(
                HttpStatus.OK,
                "Thêm danh mục thành công",
                danhMucService.updateDanhMuc(danhMuc)
            )
        );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject<String>> deleteDanhMuc(@PathVariable int id) {
        danhMucService.deleteDanhMuc(id);
        return ResponseEntity.ok(new ResponseObject<>(HttpStatus.OK, "Xóa danh mục thành công", null));
    }
}