package com.bnpstudio.bookstore.dto;


import com.bnpstudio.bookstore.entity.DanhMucEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhMucDto {
    private Integer idDanhMuc;
    private Integer idLinhVuc;
    private String tenDanhMuc;
    private String moTa;
    public DanhMucDto(DanhMucEntity danhMuc) {
        this.idDanhMuc = danhMuc.getIdDanhMuc();
        this.idLinhVuc = danhMuc.getIdLinhVuc();
        this.tenDanhMuc = danhMuc.getTenDanhMuc();
        this.moTa = danhMuc.getMoTa();
    }
}
