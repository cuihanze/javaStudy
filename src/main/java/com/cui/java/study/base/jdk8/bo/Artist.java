package com.cui.java.study.base.jdk8.bo;

import lombok.Data;

import java.util.List;

@Data
public class Artist {
    private String name;
    private List<String> members;
    private String origin;
}
