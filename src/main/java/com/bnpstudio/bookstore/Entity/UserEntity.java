package com.bnpstudio.bookstore.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NguoiDung")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdNguoiDung")
    private Integer idNguoiDung;

    @Column(name = "Name")
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @Column(name = "Email", unique = true)
    @Email(message = "Email không hợp lệ")
    private String email;

    @Column(name = "Password")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt")
    private String password;

    @Column(name = "IsAdmin")
    @ColumnDefault("false")
    private boolean isAdmin;

    @Column(name = "SoDienThoai")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại không hợp lệ")
    private String soDienThoai;

}
 