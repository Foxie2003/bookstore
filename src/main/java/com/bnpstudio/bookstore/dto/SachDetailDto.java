package com.bnpstudio.bookstore.dto;

import com.bnpstudio.bookstore.entity.SachEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SachDetailDto {
    private Integer IdSach;

    @NotNull(message = "ID danh mục không được để trống")
    private Integer IdDanhMuc;

    @NotBlank(message = "Tên sách không được để trống")
    private String tenSach;

    @NotNull(message = "Giá bán không được để trống")
    @Min(value = 0, message = "Giá bán phải lớn hơn hoặc bằng 0")
    private Float GiaBan;

    @NotBlank(message = "Tác giả không được để trống")
    private String tacGia;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private Integer SoLuong;

    @NotBlank(message = "Ảnh bìa không được để trống")
    private String AnhBia;

    private String GhiChu;

    @NotBlank(message = "Nhà cung cấp không được để trống")
    private String NhaCungCap;

    private String TenDanhMuc;

    public SachDetailDto(SachEntity sach) {
        this.IdSach = sach.getIdSach();
        this.IdDanhMuc = sach.getIdDanhMuc();
        this.tenSach = sach.getTenSach();
        this.GiaBan = sach.getGiaBan();
        this.tacGia = sach.getTacGia();
        this.SoLuong = sach.getSoLuong();
        this.AnhBia = sach.getAnhBia();
        this.GhiChu = sach.getGhiChu();
        this.NhaCungCap = sach.getNhaCungCap();
    }

}
