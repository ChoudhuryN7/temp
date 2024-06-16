package com.melo.eapp.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private Long id;
	
	private String name;
	
	private Long price;
	
	private String description;
	
	private byte[] byteimg;
	
	private String stringImg;
	
	private Long categoryId;
	
	private String categoryName;
	
//	private MultipartFile image;
	
	
    public byte[] getDecodedImage() {
        return Base64.getDecoder().decode(this.stringImg);
    }
    

}
