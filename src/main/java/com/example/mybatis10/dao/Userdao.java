package com.example.mybatis10.dao;
import com.example.mybatis10.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Userdao
{
    //회원가입
    void usersingup(Uservo uservo);
    //로그인
    Uservo login(Uservo uservo);
    //글쓰기 및 저장
    void board(Boardvo boardvo);
    //게시글 리스트 불러오기
    List<Boardvo> board_list();
    //친구리스트 불러오기
    List<Uservo> friendlist();
    //친구추가하기
    void friendadd(Friendvo friendvo);
    //친구목록확인하기
    List<Friendvo> list_result();

    //친구추가시 리스트에 없는 값 저장체크
    Friendvo friendcheck(Friendvo friendvo);


    //게시글상세 불러오기
    Boardvo read(int board_pk);
    //게시글 조회수증가
    void board_check(Boardvo boardvo);
    //좋아요 표시
    void input_like(Likevo likevo);
    //좋아요 카운트 증가
    void like_update(Boardvo boardvo);
    //카운트 아이디 판단여부
    Likevo select_like(Likevo likevo);
    //좋아요 등록삭제
    void like_remove(Likevo likevo);
    //게시판 좋아요 카운트초기화
    void like_cancel(Boardvo boardvo);

    //아이디 중볷체크
    Uservo id_check(Uservo uservo);

    //댓글등록
    void input_com(Comvo comvo);

    //게시글댓글불러오기
    List<Comvo> view_com(Comvo comvo);

    //댓글등록제한
    void com_cnt(Comvo comvo);
    //게시글삭제
    void com_remove(Comvo comvo);

    //댓글카운트 체크
    int check_cnt(Comvo comvo);

    //댓글삭제 카운트
    Comvo com_number(Comvo comvo);

    //게시글작성자 아이디 찾아오기
    Boardvo check_id(Boardvo boardvo);



}

