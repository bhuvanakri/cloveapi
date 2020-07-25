package com.covid.clove.azure.models.healthmodels.domain;

import lombok.Data;

@Data
public class Name {

        private String text;
        private String[] given;
        private String family;
        private String use;

}
