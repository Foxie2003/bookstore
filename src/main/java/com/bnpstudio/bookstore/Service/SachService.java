package com.bnpstudio.bookstore.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bnpstudio.bookstore.dto.SachDetailDto;
import com.bnpstudio.bookstore.entity.SachEntity;
import com.bnpstudio.bookstore.entity.ChiTietDonHangEntity;
import com.bnpstudio.bookstore.entity.DanhMucEntity;
import com.bnpstudio.bookstore.exception.NotFoundException;
import com.bnpstudio.bookstore.exception.NotImplementedException;
import com.bnpstudio.bookstore.exception.ValidationException;
import com.bnpstudio.bookstore.repository.ChiTietDonHangRepository;
import com.bnpstudio.bookstore.repository.DanhMucRepository;
import com.bnpstudio.bookstore.repository.SachRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@Service
public class SachService {
     @Autowired
    private Validator validator;
    @Autowired
    private SachRepository sachRepository;
    @Autowired
    private DanhMucRepository danhMucRepository;
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    private SachEntity normalizeData(SachEntity sach) {
        if (sach == null)
            return sach;
        SachEntity sachNormalized = new SachEntity();
        sachNormalized.setIdSach(sach.getIdSach());
        sachNormalized.setTenSach(sach.getTenSach() != null ? sach.getTenSach().trim() : null);
        sachNormalized.setTacGia(sach.getTacGia() != null ? sach.getTacGia().trim() : null);
        sachNormalized.setAnhBia(sach.getAnhBia() != null ? sach.getAnhBia().trim() : null);
        sachNormalized.setGhiChu(sach.getGhiChu() != null ? sach.getGhiChu().trim() : null);
        sachNormalized.setNhaCungCap(sach.getNhaCungCap() != null ? sach.getNhaCungCap().trim() : null);
        sachNormalized.setGiaBan(sach.getGiaBan());
        sachNormalized.setSoLuong(sach.getSoLuong());
        sachNormalized.setIdDanhMuc(sach.getIdDanhMuc());
        return sachNormalized;
    }

    public List<SachDetailDto> getAll() {
        List<SachEntity> sachs = sachRepository.findAll();
        return sachs.stream()
        .filter(sach -> sach.getIdSach() != 0)
        .map(SachDetailDto::new)
        .collect(Collectors.toList());
    }

    public SachDetailDto getById(int id) {
        SachEntity sach = sachRepository.findById(id);
        if (sach == null)
            throw new NotFoundException("Sách không tồn tại");
        return new SachDetailDto(sach);
    }

    public List<SachDetailDto> findByName(Pageable pageable,String name) {
        Page<SachEntity> sachs = sachRepository.findByTenSachContainingIgnoreCase(pageable, name);
        if (sachs.isEmpty()) {
            throw new NotFoundException("Không tìm thấy sách có tên: " + name);
        }
        return sachs.stream().map(SachDetailDto::new).collect(Collectors.toList());
    }

    public List<SachDetailDto> findProduct(SachDetailDto sach) {
        System.out.println(sach);

        List<SachEntity> foundProducts = sachRepository
                .findByTenSachIgnoreCaseAndTacGiaIgnoreCase(sach.getTenSach(), sach.getTacGia());
        if (foundProducts.size() > 0) {
            return foundProducts.stream().map(SachDetailDto::new).collect(Collectors.toList());
        }
        throw new NotFoundException("not found products");
    }

    public SachDetailDto insertProduct(@Valid SachDetailDto sachDetailDto) {
        SachEntity sach = new SachEntity(sachDetailDto);
        sach = normalizeData(sach);
        
        List<SachEntity> foundProducts = sachRepository
                .findByTenSachIgnoreCaseAndTacGiaIgnoreCase(sach.getTenSach(), sach.getTacGia());
        if (foundProducts.size() > 0) {
            throw new NotImplementedException("Sản phẩm đã tồn tại");
        }
        // Validate DTO
        Set<ConstraintViolation<SachEntity>> dtoViolations = validator.validate(sach);
        if (!dtoViolations.isEmpty()) {
            String errorMessages = dtoViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }
        sach.setIdSach(null);
        sachRepository.save(sach);
        return new SachDetailDto(sach);
    }

    public SachDetailDto updateProduct(@Valid SachDetailDto sachDetailDto) {
        SachEntity sach = new SachEntity(sachDetailDto);
        sach = normalizeData(sach);
        
        Optional<SachEntity> sach_old = sachRepository.findById(sach.getIdSach());
        if (sach_old.isEmpty()) {
            throw new NotFoundException("Không tìm thấy sách để cập nhật");
        }
        
        // List<SachEntity> foundProducts = sachRepository
        //         .findByTenSachIgnoreCaseAndTacGiaIgnoreCase(sach.getTenSach(), sach.getTacGia());
        // if (foundProducts.size() > 0 && sach.getTenSach().compareToIgnoreCase(sach_old.get().getTenSach()) != 0
        //         && sach.getTacGia().compareToIgnoreCase(sach_old.get().getTacGia()) != 0) {
        //     throw new NotImplementedException("Sản phẩm đã tồn tại");
        // }
        Set<ConstraintViolation<SachEntity>> dtoViolations = validator.validate(sach);
        if (!dtoViolations.isEmpty()) {
            String errorMessages = dtoViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }
        sachRepository.save(sach);
        return new SachDetailDto(sach);
    }

    public void deleteProduct(Integer id) {
        // Kiểm tra sách tồn tại
        Optional<SachEntity> sach = sachRepository.findById(id);
        if (sach.isEmpty()) {
            throw new NotFoundException("Không tìm thấy sách cần xóa");
        }

        // Lấy và cập nhật các chi tiết đơn hàng liên quan
        List<ChiTietDonHangEntity> chiTiets = chiTietDonHangRepository.findByIdSach(sach.get().getIdSach());
        
        // Cập nhật từng chi tiết đơn hàng
        for (ChiTietDonHangEntity chiTiet : chiTiets) {
            chiTiet.setIdSach(0);
            chiTietDonHangRepository.save(chiTiet);
        }

        // Xóa sách
        sachRepository.deleteById(sach.get().getIdSach());
    }

    public List<SachDetailDto> getAllPaging(Pageable pageable) {
        Page<SachEntity> sachPage = sachRepository.findAll(pageable);
        if (sachPage.isEmpty()) {
            throw new NotFoundException("Không tìm thấy dữ liệu sách cho trang này");
        }
        return sachPage.stream()
                .filter(sach -> sach.getIdSach() != 0)
                .map(SachDetailDto::new)
                .collect(Collectors.toList());
    }
    
    public List<SachDetailDto> getSachByDanhMucPaging(Pageable pageable, Integer idDanhMuc){
        Page<SachEntity> sachPage = sachRepository.findByIdDanhMuc(pageable, idDanhMuc);
        if (sachPage.isEmpty()) {
            throw new NotFoundException("Không tìm thấy sách nào trong danh mục này ở trang hiện tại");
        }
        return sachPage.stream()
                .filter(sach -> sach.getIdSach() != 0)
                .map(SachDetailDto::new)
                .collect(Collectors.toList());
    }
    public List<SachDetailDto> getSachByLinhVucPaging(Pageable pageable, Integer idLinhVuc) {
        // Lấy danh sách các danh mục thuộc lĩnh vực
        List<DanhMucEntity> listDanhMuc = danhMucRepository.findByIdLinhVuc(idLinhVuc);
        if (listDanhMuc.isEmpty()) {
            throw new NotFoundException("Không tìm thấy danh mục nào thuộc lĩnh vực này");
        }

        // Lấy danh sách id của các danh mục
        List<Integer> danhMucIds = listDanhMuc.stream()
                .map(DanhMucEntity::getIdDanhMuc)
                .collect(Collectors.toList());

        // Thêm phương thức mới vào SachRepository để lấy sách theo danh sách idDanhMuc
        Page<SachEntity> sachPage = sachRepository.findByIdDanhMucIn(pageable, danhMucIds);
        if (sachPage.isEmpty()) {
            throw new NotFoundException("Không tìm thấy sách nào thuộc lĩnh vực này ở trang hiện tại");
        }

        return sachPage.stream()
                .filter(sach -> sach.getIdSach() != 0)
                .map(SachDetailDto::new)
                .collect(Collectors.toList());
    }
}