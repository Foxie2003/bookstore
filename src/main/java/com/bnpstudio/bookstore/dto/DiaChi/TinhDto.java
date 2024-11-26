package com.bnpstudio.bookstore.dto.DiaChi;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TinhDto {
    @JsonProperty("Id")
    private String id;
    
    @JsonProperty("Name")
    private String name;
    
} 