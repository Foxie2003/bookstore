package com.bnpstudio.bookstore.entity;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietDonHang")
public class ChiTietDonHangEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")  
    private Integer id;
    
    @NotNull(message = "ID đơn hàng không được để trống")
    @Column(name = "IdDonHang")
    private Integer idDonHang; 
    @NotNull(message = "ID sách không được để trống")
    @Column(name = "IdSach")
    private Integer	idSach;
    @NotEmpty(message = "Tên sách không được để trống")
    @Column(name = "TenSach")
    private String tenSach;	    
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Column(name = "SoLuong")
    private Integer soLuong;
    @NotNull(message = "Đơn giá không được để trống")
    @Min(value = 0, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    @Column(name = "DonGia")
    private Float donGia;
   
    
}
