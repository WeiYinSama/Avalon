package net.avalon.elasticsearch;

import lombok.Data;

import java.util.List;

/**
 * @Author: Weiyin
 * @Create: 2023/3/16 - 9:13
 */
@Data
public class Employee {

    private String firstname;
    private String lastname;
    private int age;
    private String about;
    private List<String> interests;
}
