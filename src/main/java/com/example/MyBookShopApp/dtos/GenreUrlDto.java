package com.example.MyBookShopApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreUrlDto {

    private String name;
    private String parentID;
}
