package com.bnpstudio.bookstore.controller;

import com.bnpstudio.bookstore.dto.DiaChi.HuyenDto;
import com.bnpstudio.bookstore.dto.DiaChi.TinhDto;
import com.bnpstudio.bookstore.dto.DiaChi.XaDto;
import com.bnpstudio.bookstore.service.DiaChiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dia-chi")
@RequiredArgsConstructor
public class DiaChiController {
    private final DiaChiService diaChiService;

    @GetMapping("/tinh")
    public ResponseEntity<List<TinhDto>> layDanhSachTinh() {
        return ResponseEntity.ok(diaChiService.layDanhSachTinh());
    }

    @GetMapping("/tinh/{idTinh}/huyen")
    public ResponseEntity<List<HuyenDto>> layDanhSachHuyen(
            @PathVariable String idTinh) {
        return ResponseEntity.ok(diaChiService.layDanhSachHuyen(idTinh));
    }

    @GetMapping("/tinh/{idTinh}/huyen/{idHuyen}/xa")
    public ResponseEntity<List<XaDto>> layDanhSachXa(
            @PathVariable String idTinh,
            @PathVariable String idHuyen) {
        return ResponseEntity.ok(diaChiService.layDanhSachXa(idTinh, idHuyen));
    }

    @GetMapping("/tinh/{idTinh}/huyen/{idHuyen}/xa/{idXa}")
    public ResponseEntity<String> layThongTinChiTiet(
            @PathVariable String idTinh,
            @PathVariable String idHuyen,
            @PathVariable String idXa) {
        return ResponseEntity.ok(diaChiService.layThongTinChiTiet(idTinh, idHuyen, idXa));
    }
}