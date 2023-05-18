package com.epam.esm.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDto implements Linkable {
    @NotNull(message = "Id cannot be blank")
    private Long id;
    @NotNull(message = "Name cannot be blank")
    private String name;
}
