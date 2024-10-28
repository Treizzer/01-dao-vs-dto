package com.treizer.dao_vs_dto.presentation.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private Byte age;

}
