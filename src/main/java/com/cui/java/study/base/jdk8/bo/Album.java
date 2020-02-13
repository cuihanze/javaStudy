package com.cui.java.study.base.jdk8.bo;

import lombok.Data;

import java.util.List;

@Data
public class Album {
    private String name;
    private List<Track> tracks;
    private List<Artist> musicians;
}
