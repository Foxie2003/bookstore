package com.bnpstudio.bookstore.service;

import com.bnpstudio.bookstore.dto.DiaChi.TinhDto;
import com.bnpstudio.bookstore.dto.DiaChi.HuyenDto;
import com.bnpstudio.bookstore.dto.DiaChi.XaDto;
import com.bnpstudio.bookstore.entity.DiaChi.TinhEntity;
import com.bnpstudio.bookstore.entity.DiaChi.HuyenEntity;
import com.bnpstudio.bookstore.entity.DiaChi.XaEntity;
import com.bnpstudio.bookstore.exception.NotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DiaChiService {
    private List<TinhEntity> danhSachTinh;
    private static final List<String> LEVEL_TINH_TP = Arrays.asList("Thành phố", "Tỉnh");

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/templates/email/DiaChiVN.json");
            List<TinhEntity> allTinh = mapper.readValue(is, new TypeReference<List<TinhEntity>>() {});
            
            danhSachTinh = allTinh.stream()
                    .filter(tinh -> LEVEL_TINH_TP.stream()
                            .anyMatch(level -> tinh.getName().startsWith(level)))
                    .collect(Collectors.toList());
                
            log.info("Đã tải {} tỉnh/thành phố", danhSachTinh.size());
        } catch (Exception e) {
            log.error("Lỗi khi đọc file địa chỉ", e);
            danhSachTinh = new ArrayList<>();
        }
    }

    public List<TinhDto> layDanhSachTinh() {
        return danhSachTinh.stream()
                .map(tinh -> new TinhDto(tinh.getId(), tinh.getName()))
                .collect(Collectors.toList());
    }

    public List<HuyenDto> layDanhSachHuyen(String idTinh) {
        return danhSachTinh.stream()
                .filter(tinh -> tinh.getId().equals(idTinh))
                .findFirst()
                .map(tinh -> tinh.getDistricts().stream()
                        .map(huyen -> new HuyenDto(huyen.getId(), huyen.getName()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tỉnh với id = " + idTinh));
    }

    public List<XaDto> layDanhSachXa(String idTinh, String idHuyen) {
        return danhSachTinh.stream()
                .filter(tinh -> tinh.getId().equals(idTinh))
                .findFirst()
                .map(tinh -> tinh.getDistricts().stream()
                        .filter(huyen -> huyen.getId().equals(idHuyen))
                        .findFirst()
                        .map(huyen -> huyen.getWards().stream()
                                .map(xa -> new XaDto(xa.getId(), xa.getName(), xa.getLevel()))
                                .collect(Collectors.toList()))
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy huyện với id = " + idHuyen)))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tỉnh với id = " + idTinh));
    }

    public String layThongTinChiTiet(String idTinh, String idHuyen, String idXa) {
        TinhEntity tinh = danhSachTinh.stream()
                .filter(t -> t.getId().equals(idTinh))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tỉnh với id = " + idTinh));

        HuyenEntity huyen = tinh.getDistricts().stream()
                .filter(h -> h.getId().equals(idHuyen))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Không tìm thấy huyện với id = " + idHuyen));

        XaEntity xa = huyen.getWards().stream()
                .filter(x -> x.getId().equals(idXa))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Không tìm thấy xã với id = " + idXa));

        return String.format(" %s, %s, %s", 
                 xa.getName(),
                huyen.getName(),
                tinh.getName());
    }

}