package com.example.mybatis10.service;

import com.example.mybatis10.dao.Userdao;
import com.example.mybatis10.vo.Boardvo;
import com.example.mybatis10.vo.Comvo;
import com.example.mybatis10.vo.Friendvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.List;

@Service
public class Userservice_impl implements Userservice {

    @Autowired
    Userdao userdao;

    @Override
    public Boardvo read(int board_pk)
    {
//        System.out.println("상세페에지 서비스영역 확인");
        return userdao.read(board_pk);
    }

    //상세페이지 댓글달기
    @Override
    public void comments(Comvo comvo)
    {
//        System.out.println("-서비스영역- 게시글 식별");
//        System.out.println(comvo.com_pk);
//
//        System.out.println("-서비스영역- 댓글다는 유저");
//        System.out.println(comvo.com_user);
//
//        System.out.println("-서비스영역- 댓글 내용");
//        System.out.println(comvo.comments);

        userdao.input_com(comvo);
    }

    @Override
    //댓글등록시 업데이트 카운트증가
    public void com_cnt(Comvo comvo)
    {
        //System.out.println("=====카운트증가");
        //댓글등록시 카운트증가
        userdao.com_cnt(comvo);
    }

    @Override
    //댓글 갯수체크
    public int check_cnt(Comvo comvo)
    {
//        System.out.println("-서비스영역- 댓글체크");
        return userdao.check_cnt(comvo);
    }


    //상세페이지 댓글불러오기
    @Override
    public List<Comvo>view_com(Comvo comvo)
    {
//        System.out.println("-서비스영역- 댓글리스트불러오기");
//        System.out.println("-서비스영역- 아웃");
        return userdao.view_com(comvo);
    }

    //게시글삭제
    @Override
    public void com_remove(Comvo comvo)
    {
        System.out.println("@@@@@@@@@삭제2@@@@@@@@@");
        userdao.com_remove(comvo);
    }

    //게시글 작성자 아이디 찾아오기
    public Boardvo check_id(Boardvo boardvo)
    {
        return userdao.check_id(boardvo);
    }

    //친구 추가시 리스트 값 유무 체크
    public Friendvo friendcheck(Friendvo friendvo)
    {
        return userdao.friendcheck(friendvo);
    }




}
