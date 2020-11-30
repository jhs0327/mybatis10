package com.example.mybatis10.service;

import com.example.mybatis10.vo.Boardvo;
import com.example.mybatis10.vo.Comvo;
import com.example.mybatis10.vo.Friendvo;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface Userservice {
    //게시글 상세페이지

    public Boardvo read(int board_pk);
    //상세페이지 댓글
    public void comments(Comvo comvo);

    //상세페이지 댓글불러오기
    public List<Comvo> view_com(Comvo comvo);

    //상세페이지 카운트
    public void com_cnt(Comvo comvo);

    //댓글삭제
    public void com_remove(Comvo comvo);

    //댓글 카운트 체크
    public int check_cnt(Comvo comvo);

    //게시글작성자 아이디 찾아오기
    public Boardvo check_id(Boardvo boardvo);

    //친구추가시 리스트에 없는 값 저장체크
    public Friendvo friendcheck(Friendvo friendvo);


}
