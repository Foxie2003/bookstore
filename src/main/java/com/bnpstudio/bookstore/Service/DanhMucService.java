package com.bnpstudio.bookstore.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpstudio.bookstore.dto.DanhMucDto;
import com.bnpstudio.bookstore.entity.DanhMucEntity;
import com.bnpstudio.bookstore.entity.LinhVucEntity;
import com.bnpstudio.bookstore.exception.NotFoundException;
import com.bnpstudio.bookstore.exception.BadRequestException;
import com.bnpstudio.bookstore.repository.DanhMucRepository;
import com.bnpstudio.bookstore.repository.LinhVucRepository;

@Service
public class DanhMucService {
    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private LinhVucRepository linhVucRepository;

    private DanhMucDto normalizeData(DanhMucDto danhMuc) {
        if (danhMuc == null)
            return danhMuc;
        DanhMucDto danhMucNormalized = new DanhMucDto();
        danhMucNormalized.setIdDanhMuc(danhMuc.getIdDanhMuc());
        danhMucNormalized.setIdLinhVuc(danhMuc.getIdLinhVuc());
        danhMucNormalized.setTenDanhMuc(
                danhMuc.getTenDanhMuc() != null ? danhMuc.getTenDanhMuc().trim() : null);
        danhMucNormalized.setMoTa(
                danhMuc.getMoTa() != null ? danhMuc.getMoTa().trim() : null);
        return danhMucNormalized;
    }

    private Boolean validateInsertDanhMuc(DanhMucDto danhMuc) {
        if (danhMuc == null)
            throw new BadRequestException("Danh mục không được bỏ trống");
        if (danhMuc.getIdLinhVuc() == null)
            throw new BadRequestException("Id lĩnh vực không được bỏ trống");
        Optional<LinhVucEntity> linhVucFound = linhVucRepository.findById(danhMuc.getIdLinhVuc());
        if (linhVucFound.isEmpty())
            throw new BadRequestException("Lĩnh vực không tồn tại");
        if (danhMuc.getTenDanhMuc() == null || danhMuc.getTenDanhMuc().isEmpty())
            throw new BadRequestException("Tên danh mục không được để trống");
        Optional<DanhMucEntity> danhMucFound = danhMucRepository.findByTenDanhMuc(danhMuc.getTenDanhMuc());
        if (danhMucFound.isPresent())
            throw new BadRequestException("Tên danh mục đã tồn tại");
        return true;
    }

    private Boolean validateUpdateDanhMuc(DanhMucDto danhMuc) {
        if (danhMuc == null)
            throw new BadRequestException("Danh mục không được bỏ trống");
        Optional<DanhMucEntity> danhMucOld = danhMucRepository.findById(danhMuc.getIdDanhMuc());
        if (danhMucOld.isEmpty())
            throw new BadRequestException("Danh mục không tồn tại");
        if (danhMuc.getIdLinhVuc() == null)
            throw new BadRequestException("Id lĩnh vực không được bỏ trống");
        Optional<LinhVucEntity> linhVucFound = linhVucRepository.findById(danhMuc.getIdLinhVuc());
        if (linhVucFound.isEmpty())
            throw new BadRequestException("Lĩnh vực không tồn tại");
        if (danhMuc.getTenDanhMuc() == null || danhMuc.getTenDanhMuc().isEmpty())
            throw new BadRequestException("Tên danh mục không được để trống");
        Optional<DanhMucEntity> danhMucFound = danhMucRepository.findByTenDanhMuc(danhMuc.getTenDanhMuc());
        if (danhMucFound.isPresent() && !danhMuc.getTenDanhMuc().equals(danhMucOld.get().getTenDanhMuc()))
            throw new BadRequestException("Tên danh mục đã tồn tại");
        return true;
    }

    public List<DanhMucDto> getAll() {
        List<DanhMucEntity> danhMucs = danhMucRepository.findAll();
        if (danhMucs.isEmpty())
            throw new NotFoundException("Không tìm thấy danh mục nào");
        return danhMucs.stream()
                .map(DanhMucDto::new)
                .collect(Collectors.toList());
    }

    public List<DanhMucDto> getByIdDanhMuc(Integer idLinhVuc) {
        List<DanhMucEntity> danhMucs = danhMucRepository.findByIdLinhVuc(idLinhVuc);
        if (danhMucs.isEmpty())
            throw new NotFoundException("Không tìm thấy danh mục nào có id lĩnh vực = " + idLinhVuc);
        return danhMucs.stream()
                .map(DanhMucDto::new)
                .collect(Collectors.toList());
    }

    public DanhMucDto getById(Integer id) {
        Optional<DanhMucEntity> danhMuc = danhMucRepository.findById(id);
        if (danhMuc.isEmpty())
            throw new NotFoundException("Không tìm thấy lĩnh vực nào có id = " + id);
        return new DanhMucDto(danhMuc.get());
    }

    public DanhMucDto insertDanhMuc(DanhMucDto danhMuc) {
        danhMuc = normalizeData(danhMuc);
        validateInsertDanhMuc(danhMuc);
        DanhMucEntity danhMucEntity = new DanhMucEntity(danhMuc);
        danhMucEntity.setIdDanhMuc(null);
        danhMucRepository.save(danhMucEntity);
        return new DanhMucDto(danhMucEntity);
    }

    public DanhMucDto updateDanhMuc(DanhMucDto danhMuc) {
        danhMuc = normalizeData(danhMuc);
        validateUpdateDanhMuc(danhMuc);
        DanhMucEntity danhMucEntity = new DanhMucEntity(danhMuc);
        danhMucRepository.save(danhMucEntity);
        return danhMuc;
    }
}
