package com.bnpstudio.bookstore.entity;

import com.bnpstudio.bookstore.dto.SachDetailDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Sach")
public class SachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSach")
    private Integer IdSach;

    @NotNull(message = "ID danh mục không được để trống")
    @Column(name = "IdDanhMuc")
    private Integer idDanhMuc;

    @NotEmpty(message = "Tên sách không được để trống")
    @Column(name = "TenSach")
    private String tenSach;

    @NotNull(message = "Giá bán không được để trống")
    @Min(value = 0, message = "Giá bán phải lớn hơn hoặc bằng 0")
    @Column(name = "GiaBan")
    private Float giaBan;

    @NotEmpty(message = "Tên tác giả không được để trống")
    @Column(name = "TacGia")
    private String tacGia;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "AnhBia")
    private String anhBia;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "NhaCungCap")
    private String nhaCungCap;

    

    public SachEntity(SachDetailDto sachDetail) {
        this.IdSach = sachDetail.getIdSach();
        this.idDanhMuc = sachDetail.getIdDanhMuc();
        this.tenSach = sachDetail.getTenSach();
        this.giaBan = sachDetail.getGiaBan();
        this.tacGia = sachDetail.getTacGia();
        this.soLuong = sachDetail.getSoLuong();
        this.anhBia = sachDetail.getAnhBia();
        this.ghiChu = sachDetail.getGhiChu();
        this.nhaCungCap = sachDetail.getNhaCungCap();
    }
    
   
}