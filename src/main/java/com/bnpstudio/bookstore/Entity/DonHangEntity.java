package com.bnpstudio.bookstore.entity;

import java.sql.Date;

import com.bnpstudio.bookstore.dto.DonHangDetailDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DonHang")
public class DonHangEntity {
    @Id
    @Column(name = "IdDonHang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDonHang;
    
    @Column(name = "IDNguoiDung")
    @NotNull(message = "id người dùng không được để trống")
    private Integer idNguoiDung;

    @Column(name = "TenNguoiNhan")
    @NotEmpty(message = "tên người nhận không được để trống")
    private String tenNguoiNhan;

    @Column(name = "SoDienThoai")
    @NotEmpty(message = "số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại phải có 10 chữ số")
    private String soDienThoai;

    @Column(name = "TongHoaDon")
    @NotNull(message = "tổng đơn hàng không được để trống")
    @Min(value = 0, message = "Tổng hóa đơn phải lớn hơn hoặc bằng 0")
    private Float tongHoaDon;

    @Column(name = "DiaChi")
    @NotEmpty(message = "địa chỉ nhận hàng không được để trống")
    private String diaChi;

    @Column(name = "NgayTao")
    private Date ngayTao;
    
    @Column(name = "NgayNhan")
    private Date ngayNhan;
    
    @Column(name = "TrangThai")
    private String trangThai;
    
    @Column(name = "PhiVanChuyen") 
    private Float phiVanChuyen;
    
    @Column(name = "PhuongThucThanhToan")
    private String phuongThucThanhToan;
    
    @Column(name = "MaGiamGia")
    private String maGiamGia;
    
    @Column(name = "GhiChu")
    private String ghiChu; 

    public DonHangEntity(DonHangDetailDto detailDto) {
        this.idDonHang = detailDto.getIdDonHang();
        this.idNguoiDung = detailDto.getIdNguoiDung();
        this.tenNguoiNhan = detailDto.getTenNguoiNhan();
        this.soDienThoai = detailDto.getSoDienThoai();
        this.tongHoaDon = detailDto.getTongHoaDon();
        this.diaChi = detailDto.getDiaChi();
        this.ngayTao = detailDto.getNgayTao();
        this.ngayNhan = detailDto.getNgayNhan();
        this.trangThai = detailDto.getTrangThai();
        this.phiVanChuyen = detailDto.getPhiVanChuyen();
        this.phuongThucThanhToan = detailDto.getPhuongThucThanhToan();
        this.maGiamGia = detailDto.getMaGiamGia();
        this.ghiChu = detailDto.getGhiChu();
    }

    
}
