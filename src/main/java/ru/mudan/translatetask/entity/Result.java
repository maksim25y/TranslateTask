package ru.mudan.translatetask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Long id;
    private String ipAddress;
    private String input;
    private String answer;
}
