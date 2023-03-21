package net.avalon.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Weiyin
 * @Create: 2023/3/21 - 11:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {

    private Long account_number;
    private Long balance;
    private String firstname;
    private String lastname;
    private Integer age;
    private String gender;
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;
}
