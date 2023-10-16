package com.ivotasevski.springcloudstreamdemo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Item {
    private String id;
    private LocalDate processedTimestamp;
}
