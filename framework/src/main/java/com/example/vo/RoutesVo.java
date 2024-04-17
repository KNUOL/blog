package com.example.vo;

import com.example.pojo.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesVo {
    private List<Menu> menus;

}
