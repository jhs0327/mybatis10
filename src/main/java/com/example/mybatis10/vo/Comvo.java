package com.example.mybatis10.vo;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class Comvo {

    //게시글번호
    public Integer com_pk;
    //게시글 댓글작성 아이디
    public String com_user;
    //게시글 댓글내용
    public String comments;
    //댓글작성 번호
    public Integer com_num;
    //댓글 제한카운트
    public int com_count;

}
