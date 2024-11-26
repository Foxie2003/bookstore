package com.bnpstudio.bookstore.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bnpstudio.bookstore.dto.ChiTietDonHangDetailDto;
import com.bnpstudio.bookstore.dto.DonHangDetailDto;
import com.bnpstudio.bookstore.entity.ChiTietDonHangEntity;
import com.bnpstudio.bookstore.entity.DonHangEntity;
import com.bnpstudio.bookstore.entity.SachEntity;
import com.bnpstudio.bookstore.exception.NotFoundException;
import com.bnpstudio.bookstore.exception.ValidationException;
import com.bnpstudio.bookstore.repository.ChiTietDonHangRepository;
import com.bnpstudio.bookstore.repository.DonHangRepository;
import com.bnpstudio.bookstore.repository.SachRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class DonHangService {
    @Autowired
    DonHangRepository donHangRepository;
    @Autowired
    ChiTietDonHangRepository chiTietDonHangRepository;
    @Autowired
    SachRepository sachRepository;
    public List<DonHangDetailDto> getAll() {
        List<DonHangEntity> donHangs = donHangRepository.findAll();
        System.out.println("donhangs: " + donHangs);
        return donHangs.stream()
                .map(DonHangDetailDto::new)
                .collect(Collectors.toList());
    }

    public List<DonHangDetailDto> getDonHangByTrangThai(Pageable pageable ,String trangThai) {
        Page<DonHangEntity> donHangs = donHangRepository.findByTrangThaiIgnoreCase(trangThai.trim(),pageable);
        System.out.println("donhangs: " + donHangs);
        return donHangs.stream()
                .map(DonHangDetailDto::new)
                .collect(Collectors.toList());
    }
    public DonHangDetailDto updateTrangThaiDonHang(int id, String trangThai){

        
        if (trangThai == null || trangThai.trim().isEmpty()) {
            throw new IllegalArgumentException("Trạng thái đơn hàng không được để trống");
        }
        
        DonHangEntity dh = donHangRepository.findById(id);
        if(dh == null) {
            throw new NotFoundException("Không tồn tại đơn hàng có mã là: " + id);
        }
        
        dh.setTrangThai(trangThai);
        donHangRepository.save(dh);
        return new DonHangDetailDto(dh);
    
    }
    public DonHangDetailDto getById (Integer id){
        if(id == null) {
            throw new IllegalArgumentException("Id không được để trống");
        }
        DonHangEntity dh = donHangRepository.findById(id).orElse(null);
        List<ChiTietDonHangEntity> chiTietDonHangEntities = chiTietDonHangRepository.findByIdDonHang(id);
        List<ChiTietDonHangDetailDto> chiTietDonHangDetailDtos = chiTietDonHangEntities.stream()
                .map(entity -> new ChiTietDonHangDetailDto(entity))
                .collect(Collectors.toList());
        DonHangDetailDto donHangDetailDto = new DonHangDetailDto(dh);
        donHangDetailDto.setChiTietDonHangs(chiTietDonHangDetailDtos);
        return donHangDetailDto;
    }
    @Autowired
    private Validator validator;
    public DonHangDetailDto insertDonHang(DonHangDetailDto detail) {
        // Validate dữ liệu đầu vào
        if (detail.getIdNguoiDung() == null) {
            throw new IllegalArgumentException("ID người dùng không được để trống");
        }
        if (detail.getTenNguoiNhan() == null || detail.getTenNguoiNhan().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên người nhận không được để trống");
        }
        if (detail.getSoDienThoai() == null || detail.getSoDienThoai().trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống");
        }
        if (detail.getDiaChi() == null || detail.getDiaChi().trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống");
        }
        // Tạo đơn hàng mới
        DonHangEntity donHang = new DonHangEntity(detail);
        Set<ConstraintViolation<DonHangEntity>> dtoViolations = validator.validate(donHang);
       if (!dtoViolations.isEmpty()) {
           String errorMessages = dtoViolations.stream()
               .map(ConstraintViolation::getMessage)
               .collect(Collectors.joining(", "));
           throw new ValidationException(errorMessages);
       }
        
        // Set các giá trị mặc định
        donHang.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
        donHang.setTrangThai("Chờ xác nhận");
        donHang.setIdDonHang(null);
        // Lưu đơn hàng
        donHang = donHangRepository.save(donHang);
        // Lưu chi tiết đơn hàng
        if (detail.getChiTietDonHangs() != null && !detail.getChiTietDonHangs().isEmpty()) {
            for (ChiTietDonHangDetailDto chiTiet : detail.getChiTietDonHangs()) {
                ChiTietDonHangEntity chiTietEntity = new ChiTietDonHangEntity();
                chiTietEntity.setIdDonHang(donHang.getIdDonHang());
                chiTietEntity.setIdSach(chiTiet.getIdSach());
                chiTietEntity.setTenSach(chiTiet.getTenSach());
                chiTietEntity.setSoLuong(chiTiet.getSoLuong());
                chiTietEntity.setDonGia(chiTiet.getDonGia());
                SachEntity sach = sachRepository.findById(chiTiet.getIdSach()).orElse(null);
                if(sach==null){
                    throw new NotFoundException("Không tồn tại sách có id= "+ chiTiet.getIdSach());
                }
                if(sach.getSoLuong()< chiTiet.getSoLuong()){
                    throw new ValidationException("Số lượng sách trong kho không đủ");
                }
                sach.setSoLuong(sach.getSoLuong()-chiTiet.getSoLuong());
                sachRepository.save(sach);
                chiTietDonHangRepository.save(chiTietEntity);
            }
        }
        
        // Trả về thông tin đơn hàng đã tạo
        return getById(donHang.getIdDonHang());
    }
}
