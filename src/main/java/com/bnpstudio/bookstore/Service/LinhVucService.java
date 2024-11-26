package com.bnpstudio.bookstore.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpstudio.bookstore.repository.LinhVucRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import com.bnpstudio.bookstore.dto.LinhVucDto;
import com.bnpstudio.bookstore.entity.LinhVucEntity;
import com.bnpstudio.bookstore.exception.NotFoundException;
import com.bnpstudio.bookstore.exception.ValidationException;
import com.bnpstudio.bookstore.exception.BadRequestException;

@Service
public class LinhVucService {
    @Autowired
    private LinhVucRepository linhVucRepository;

    private LinhVucDto normalizeData(LinhVucDto linhVuc) {
        if (linhVuc == null)
            return linhVuc;
        LinhVucDto linhVucNormalized = new LinhVucDto();
        linhVucNormalized.setIdLinhVuc(linhVuc.getIdLinhVuc());
        linhVucNormalized.setTenLinhVuc(
                linhVuc.getTenLinhVuc() != null ? linhVuc.getTenLinhVuc().trim() : null);
        linhVucNormalized.setMoTa(
                linhVuc.getMoTa() != null ? linhVuc.getMoTa().trim() : null);
        return linhVucNormalized;
    }

    private Boolean validateInsertLinhVuc(LinhVucDto linhVuc) {
        if (linhVuc == null)
            throw new BadRequestException("Lĩnh vực không được bỏ trống");
        if (linhVuc.getTenLinhVuc() == null || linhVuc.getTenLinhVuc().isEmpty())
            throw new BadRequestException("Tên lĩnh vực không được bỏ trống");
        Optional<LinhVucEntity> linhVucFound = linhVucRepository.findByTenLinhVucIgnoreCase(linhVuc.getTenLinhVuc());
        if (linhVucFound.isPresent())
            throw new BadRequestException("Tên lĩnh vực đã tồn tại");
        return true;
    }

    private Boolean validateUpdateLinhVuc(LinhVucDto linhVuc) {
        if (linhVuc == null)
            throw new BadRequestException("Lĩnh vực không được bỏ trống");
        if (linhVuc.getIdLinhVuc() == null)
            throw new BadRequestException("Id lĩnh vực không được bỏ trống");
        if (linhVuc.getTenLinhVuc() == null || linhVuc.getTenLinhVuc().isEmpty())
            throw new BadRequestException("Tên lĩnh vực không được bỏ trống");
        Optional<LinhVucEntity> linhVucOld = linhVucRepository.findById(linhVuc.getIdLinhVuc());
        if (linhVucOld.isEmpty())
            throw new BadRequestException("Lĩnh vực có id = " + linhVuc.getIdLinhVuc() + " không tồn tại");
        Optional<LinhVucEntity> linhVucFound = linhVucRepository.findByTenLinhVucIgnoreCase(linhVuc.getTenLinhVuc());
        if (linhVucFound.isPresent() && !linhVuc.getTenLinhVuc().equalsIgnoreCase(linhVucOld.get().getTenLinhVuc()))
            throw new BadRequestException("Tên lĩnh vực đã tồn tại");
        return true;
    }

    public List<LinhVucDto> getAll() {
        List<LinhVucEntity> linhVucs = linhVucRepository.findAll();
        if (linhVucs.isEmpty())
            throw new NotFoundException("Không tìm thấy lĩnh vực nào");
        return linhVucs.stream()
                .map(LinhVucDto::new)
                .filter(linhVuc -> linhVuc.getIdLinhVuc() != 0)
                .collect(Collectors.toList());
    }

    public LinhVucDto getById(Integer id) {
        Optional<LinhVucEntity> linhVuc = linhVucRepository.findById(id);
        if (linhVuc.isEmpty())
            throw new NotFoundException("Không tìm thấy lĩnh vực nào với id = " + id);
        return new LinhVucDto(linhVuc.get());
    }
    @Autowired
    private Validator validator;
    public LinhVucDto insertLinhVuc(LinhVucDto linhVuc) {
        linhVuc = normalizeData(linhVuc);
        validateInsertLinhVuc(linhVuc);
        LinhVucEntity linhVucEntity = new LinhVucEntity(linhVuc);
        Set<ConstraintViolation<LinhVucEntity>> dtoViolations = validator.validate(linhVucEntity);
       if (!dtoViolations.isEmpty()) {
           String errorMessages = dtoViolations.stream()
               .map(ConstraintViolation::getMessage)
               .collect(Collectors.joining(", "));
           throw new ValidationException(errorMessages);
       }
        linhVucEntity.setIdLinhVuc(null);
        linhVucRepository.save(linhVucEntity);
        return new LinhVucDto(linhVucEntity);
    }

    public LinhVucDto updateLinhVuc(LinhVucDto linhVuc) {
        linhVuc = normalizeData(linhVuc);
        validateUpdateLinhVuc(linhVuc);
        LinhVucEntity linhVucEntity = new LinhVucEntity(linhVuc);
        Set<ConstraintViolation<LinhVucEntity>> dtoViolations = validator.validate(linhVucEntity);
        if (!dtoViolations.isEmpty()) {
            String errorMessages = dtoViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }
        linhVucRepository.save(linhVucEntity);
        return linhVuc;
    }

    public LinhVucDto deleteLinhVuc(Integer id) {
        if (id == null)
            throw new BadRequestException("Id lĩnh vực không được bỏ trống");
        Optional<LinhVucEntity> linhVuc = linhVucRepository.findById(id);
        if (linhVuc.isEmpty())
            throw new BadRequestException("Lĩnh vực có id = " + id + " không tồn tại");
        linhVucRepository.deleteById(id);
        return new LinhVucDto(linhVuc.get());
    }
}
