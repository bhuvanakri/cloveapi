package com.covid.clove.azure.models.healthmodels.domain;

import lombok.Data;

import java.util.List;

@Data
public class Code {
    private String text;
    private List<Coding> coding;
}
