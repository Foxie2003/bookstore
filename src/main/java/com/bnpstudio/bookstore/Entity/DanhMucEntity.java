package com.bnpstudio.bookstore.entity;



import com.bnpstudio.bookstore.dto.DanhMucDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DanhMuc")
public class DanhMucEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDanhMuc")
    private Integer idDanhMuc;

    @Column(name = "IdLinhVuc")
    private Integer idLinhVuc;

    @Column(name = "TenDanhMuc")
    private String tenDanhMuc;

    @Column(name = "MoTa")
    private String moTa;

    public DanhMucEntity(DanhMucDto danhMuc) {
        this.idDanhMuc = danhMuc.getIdDanhMuc();
        this.idLinhVuc = danhMuc.getIdLinhVuc();
        this.tenDanhMuc = danhMuc.getTenDanhMuc();
        this.moTa = danhMuc.getMoTa();
    }
}
