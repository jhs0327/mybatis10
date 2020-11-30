package com.example.mybatis10.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Boardvo
{

    public String id;
    public String board_title;
    public String board_content;

    public int board_pk;
    public int like_count;
    public int board_join;

}
