package com.bnpstudio.bookstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnpstudio.bookstore.dto.SachDetailDto;
import com.bnpstudio.bookstore.entity.ResponseObject;
import com.bnpstudio.bookstore.entity.SachEntity;
import com.bnpstudio.bookstore.service.SachService;

import org.springframework.web.bind.annotation.RequestBody;

import com.bnpstudio.bookstore.exception.BadRequestException;
import com.bnpstudio.bookstore.exception.NotFoundException;
import com.bnpstudio.bookstore.exception.NotImplementedException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/sach")
public class SachController {
    @Autowired
    private SachService sachService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(sachService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping({ "/getById/{id}", "/getById/" })
    public ResponseEntity<ResponseObject> getById(@PathVariable(required = false) Integer id) {
        if (id == null) {
            throw new BadRequestException("id is null");
        }
        SachDetailDto sachDetailDto = sachService.getById(id);
        return ResponseEntity.ok(new ResponseObject(HttpStatus.OK, "Tìm thấy sách", sachDetailDto));
    }

    @GetMapping("/getByName/{name}")
    ResponseEntity<ResponseObject> getByName(@PathVariable String name){
        List<SachDetailDto> sachs = sachService.findByName(name);
        if (sachs.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK, "query product succsessfully", sachs));
        } else {
            throw new NotFoundException("cannot found product with name= " + name);
        }
    }
    @PostMapping("/getByNameAndTacGia")
    List<SachDetailDto> getByNameAndTacGia(@RequestBody SachEntity sach) {
        System.out.println("phg:"+sach.getTenSach());
        try {
            List<SachDetailDto> sachs = sachService.findProduct(sach);
            return sachs;
            
        } catch (Exception e) {
            throw e;// TODO: handle exception
        }
    }
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertSach(@RequestBody SachEntity sach) {
        System.out.println("phg:"+sach.getTenSach());
        try {
            SachDetailDto sachs = sachService.insertProduct(sach);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK,"insert product successfully",sachs));
            
        } catch (Exception e) {
            throw e;// TODO: handle exception
        }
    }
}




