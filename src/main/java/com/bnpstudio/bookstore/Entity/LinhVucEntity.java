package com.bnpstudio.bookstore.entity;

import com.bnpstudio.bookstore.dto.LinhVucDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LinhVuc")
public class LinhVucEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLinhVuc")
    private Integer idLinhVuc;

    @NotEmpty(message = "Tên lĩnh vực không được để trống")
    @Column(name = "TenLinhVuc", unique = true)
    private String tenLinhVuc;

    @Column(name = "MoTa")
    private String moTa;

    public LinhVucEntity(LinhVucDto linhVuc) {
        this.idLinhVuc = linhVuc.getIdLinhVuc();
        this.tenLinhVuc = linhVuc.getTenLinhVuc();
        this.moTa = linhVuc.getMoTa();
    }
}
