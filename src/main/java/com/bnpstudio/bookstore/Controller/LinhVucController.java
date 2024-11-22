package com.bnpstudio.bookstore.controller;

import com.bnpstudio.bookstore.dto.LinhVucDto;
import com.bnpstudio.bookstore.entity.ResponseObject;
import com.bnpstudio.bookstore.service.LinhVucService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/linhVuc")
@PreAuthorize("isAuthenticated()")
public class LinhVucController {
        @Autowired
        private LinhVucService linhVucService;

        @SuppressWarnings({ "rawtypes", "unchecked" })
        @GetMapping("/getAll")
        public ResponseEntity<ResponseObject<List<LinhVucDto>>> getAll() {
                return ResponseEntity.ok(
                                new ResponseObject(
                                                HttpStatus.OK,
                                                "Get tất cả lĩnh vực thành công",
                                                linhVucService.getAll()));
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        @GetMapping("/getById/{id}")
        public ResponseEntity<ResponseObject> getById(@PathVariable int id) {
                return ResponseEntity.ok(
                                new ResponseObject(
                                                HttpStatus.OK,
                                                "Get lĩnh vực có id = " + id + " thành công",
                                                linhVucService.getById(id)));
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        @PostMapping("/insert")
        public ResponseEntity<ResponseObject<LinhVucDto>> insertLinhVuc(@RequestBody LinhVucDto linhVuc) {
                return ResponseEntity.ok(
                                new ResponseObject(
                                                HttpStatus.OK,
                                                "Thêm lĩnh vực thành công",
                                                linhVucService.insertLinhVuc(linhVuc)));
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        @PutMapping("/update")
        public ResponseEntity<ResponseObject> updateLinhVuc(@RequestBody LinhVucDto linhVuc) {
                return ResponseEntity.ok(
                                new ResponseObject(
                                                HttpStatus.OK,
                                                "Cập nhật lĩnh vực thành công",
                                                linhVucService.updateLinhVuc(linhVuc)));
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ResponseObject> deleteLinhVuc(@PathVariable int id) {
                return ResponseEntity.ok(
                                new ResponseObject(
                                                HttpStatus.OK,
                                                "Xóa lĩnh vực thành công",
                                                linhVucService.deleteLinhVuc(id)));
        }
}
