package com.bnpstudio.bookstore.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bnpstudio.bookstore.entity.SachEntity;

@Repository
public interface SachRepository extends JpaRepository<SachEntity, Integer> {
    @SuppressWarnings("null")
    List<SachEntity> findAll();
    SachEntity findById(int id);
    Page<SachEntity> findByTenSachContainingIgnoreCase(Pageable pageable,String tenSach);

    List<SachEntity> findByTenSachIgnoreCaseAndTacGiaIgnoreCase(String tenSach,String tacGia);
    
    Page<SachEntity> findByIdDanhMuc(Pageable pageable, Integer idDanhMuc);

    @SuppressWarnings("null")
    Page<SachEntity> findAll(Pageable pageable);

    Page<SachEntity> findByIdDanhMucIn(Pageable pageable, List<Integer> idDanhMucs);

    // Page<SachEntity> findByIdSachNot(Integer idSach, Pageable pageable);
    @Query("SELECT s FROM SachEntity s WHERE s.IdSach != :id")
    Page<SachEntity> findByIdSachNot(@Param("id") Integer id, Pageable pageable);
}
