package com.bnpstudio.bookstore.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

import com.bnpstudio.bookstore.entity.DonHangEntity;

@Repository
public interface DonHangRepository extends JpaRepository<DonHangEntity,Integer> {
    //get all
    @SuppressWarnings("null")
    List<DonHangEntity> findAll();
    //get theo trạng thái
    Page<DonHangEntity> findByTrangThaiIgnoreCase(String trangThai,Pageable pageable );
    //chỉnh sửa trạng thái: xác nhận - hủy - đã gửi - đã nhận
    // DonHangEntity updateTrangThaiDonHangEntity(Integer id, String trangThai);
    //get chi tiết

    DonHangEntity findById(int id);
    //print đơn hàng + vận chuyểnint
}
