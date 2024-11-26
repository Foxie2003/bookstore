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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

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

    @NotNull(message = "ID lĩnh vực không được để trống")
    @Column(name = "IdLinhVuc")
    private Integer idLinhVuc;

    @NotEmpty(message = "Tên danh mục không được để trống")
    @Column(name = "TenDanhMuc", unique = true)
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
