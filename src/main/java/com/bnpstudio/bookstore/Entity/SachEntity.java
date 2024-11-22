package com.bnpstudio.bookstore.entity;

import com.bnpstudio.bookstore.dto.SachDetailDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "IdDanhMuc")
    private Integer idDanhMuc;

    @Column(name = "TenSach")
    private String tenSach;

    @Column(name = "GiaBan")
    private Float giaBan;

    @Column(name = "TacGia")
    private String tacGia;

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