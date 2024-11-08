package com.bnpstudio.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "LinhVuc")
public class LinhVucEntity {
    @Id
    @Column(name = "IdLinhVuc")
    private int idLinhVuc;

    @Column(name = "TenLinhVuc")
    private String tenLinhVuc;

    @Column(name = "MoTa")
    private String moTa;

    public LinhVucEntity() {
    }

    public LinhVucEntity(int idLinhVuc, String tenLinhVuc, String moTa) {
        this.idLinhVuc = idLinhVuc;
        this.tenLinhVuc = tenLinhVuc;
        this.moTa = moTa;
    }

    public int getIdLinhVuc() {
        return idLinhVuc;
    }

    public void setIdLinhVuc(int idLinhVuc) {
        this.idLinhVuc = idLinhVuc;
    }

    public String getTenLinhVuc() {
        return tenLinhVuc;
    }

    public void setTenLinhVuc(String tenLinhVuc) {
        this.tenLinhVuc = tenLinhVuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return "LinhVucEntity [idLinhVuc=" + idLinhVuc + ", tenLinhVuc=" + tenLinhVuc + ", moTa=" + moTa + "]";
    }
}
