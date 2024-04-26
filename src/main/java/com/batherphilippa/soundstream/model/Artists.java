package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Artists - el objeto devuelve un array de artistas en un álbum.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artists {
    private String href;
    private List<Items> items;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
}
