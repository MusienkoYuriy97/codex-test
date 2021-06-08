package com.codex.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApi {
    private User user;
    private List<Product> product;
    private String message;
}
