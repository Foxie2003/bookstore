package com.bnpstudio.bookstore.entity;

import com.bnpstudio.bookstore.dto.SachDetailDto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Sach")
public class SachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSach")
    private Integer IdSach;

    @Column(name = "IdDanhMuc")
    @NotNull(message = "ID danh mục không được để trống")
    private Integer IdDanhMuc;

    @Column(name = "TenSach")
    @NotBlank(message = "Tên sách không được để trống")
    private String tenSach;

    @Column(name = "GiaBan")
    @NotNull(message = "Giá bán không được để trống")
    @Min(value = 0, message = "Giá bán phải lớn hơn hoặc bằng 0")
    private Float GiaBan;

    @Column(name = "TacGia")
    @NotBlank(message = "Tên tác giả không được để trống")
    private String tacGia;

    @Column(name = "SoLuong")
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private Integer SoLuong;

    @Column(name = "AnhBia")
    @NotBlank(message = "Ảnh bìa không được để trống")
    private String AnhBia;

    @Column(name = "GhiChu")
    private String GhiChu;

    @Column(name = "NhaCungCap")
    @NotBlank(message = "Nhà cung cấp không được để trống")
    private String NhaCungCap;

    public SachEntity() {
    }

    public SachEntity(Integer idSach, Integer idDanhMuc, String tenSach, Float giaBan, String tacGia, Integer soLuong,
            String anhBia, String ghiChu, String nhaCungCap) {
        this.IdSach = idSach;
        this.IdDanhMuc = idDanhMuc;
        this.tenSach = tenSach;
        this.GiaBan = giaBan;
        this.tacGia = tacGia;
        this.SoLuong = soLuong;
        this.AnhBia = anhBia;
        this.GhiChu = ghiChu;
        this.NhaCungCap = nhaCungCap;
    }

    public SachEntity(SachDetailDto sachDetail) {
        this.IdSach = sachDetail.getIdSach();
        this.IdDanhMuc = sachDetail.getIdDanhMuc();
        this.tenSach = sachDetail.getTenSach();
        this.GiaBan = sachDetail.getGiaBan();
        this.tacGia = sachDetail.getTacGia();
        this.SoLuong = sachDetail.getSoLuong();
        this.AnhBia = sachDetail.getAnhBia();
        this.GhiChu = sachDetail.getGhiChu();
        this.NhaCungCap = sachDetail.getNhaCungCap();
    }

    public String getAnhBia() {
        return AnhBia;
    }

    public void setAnhBia(String anhBia) {
        this.AnhBia = anhBia;
    }

    public Integer getIdSach() {
        return IdSach;
    }

    public void setIdSach(Integer idSach) {
        this.IdSach = idSach;
    }

    public Integer getIdDanhMuc() {
        return IdDanhMuc;
    }

    public void setIdDanhMuc(Integer idDanhMuc) {
        this.IdDanhMuc = idDanhMuc;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public Float getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(float giaBan) {
        this.GiaBan = giaBan;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.SoLuong = soLuong;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.GhiChu = ghiChu;
    }

    public String getNhaCungCap() {
        return NhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.NhaCungCap = nhaCungCap;
    }
}