package com.example.mybatis10.controller;

import com.example.mybatis10.dao.Userdao;
import com.example.mybatis10.service.Userservice;
import com.example.mybatis10.vo.*;
import com.sun.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.awt.*;
import java.util.List;

@Controller
public class Usercontroller
{
    @Autowired
    Userdao userdao;

    @Autowired
    Userservice userservice;

    //글 등록 대기페이지
    @RequestMapping(value = "board_write", method = RequestMethod.GET)
    public String board_wait()
    {
        System.out.println("게시판등록");
        return "board_write";
    }

    //글 등록페이지
    @RequestMapping(value = "board_write", method = RequestMethod.POST)
    public String board_whrite(Boardvo boardvo, HttpSession session)
    {
        boardvo.setId(session.getAttribute("user_id").toString());
        session.setAttribute("board_id", boardvo.getId());
        session.setAttribute("board_pk", boardvo.getBoard_pk());

        System.out.println("유저 아이디 확인");
        System.out.println(session.getAttribute("user_id").toString());
        System.out.println("게시판 아이디 확인");
        System.out.println(boardvo.getId());

        System.out.println();
        System.out.println("/////");

        System.out.println(boardvo.getId());

        System.out.println("게시판등록");
        userdao.board(boardvo);
        return "board_write";
    }

    //게시판 글 리스트 붏러오기
    @RequestMapping(value = "board_list", method = {RequestMethod.GET,RequestMethod.POST})
    public String board_list(Model model)
    {
        System.out.println("게시판 리스트");
        List<Boardvo> board_list = userdao.board_list();
        //뷰 -> 키,값 전송
        model.addAttribute("board_list", board_list);
        return "board_list";
    }

    //게시글상세보기
    @RequestMapping(value = "board_detail")
    public String read(@RequestParam("board_pk") int board_pk, HttpSession session, Boardvo boardvo ,Model model, Comvo comvo)
    {
        session.setAttribute("board_p", boardvo.getBoard_pk());
        //게시글 조회수 증가
        userdao.board_check(boardvo);
        //게시글 상세보기 모듈에 담
        model.addAttribute("board_number", userservice.read(board_pk));
        System.out.println("-컨트롤러영역- 게시글상세페이지");

        System.out.printf(session.getAttribute("user_id").toString());

        //게시판, 유저 정보받아오기
        comvo.setCom_pk((Integer)session.getAttribute("board_p"));
        comvo.setCom_user(session.getAttribute("user_id").toString());
        int count = userservice.check_cnt(comvo);
        //댓글카운트 여부확인
        model.addAttribute("count", count);

        //댓글출력
        comvo.setCom_pk((Integer)session.getAttribute("board_p"));
        //댓글ai담기
        comvo.setCom_num((Integer)session.getAttribute("com_num"));
        System.out.println("상세페이지 댓글 불러오기 확인");
        comvo.getCom_num();
        List<Comvo> view_com = userservice.view_com(comvo);

        System.out.println("@@@@@@");
        System.out.println(userservice.view_com(comvo).toString());
        model.addAttribute("com",view_com);

        return "board_detail";
    }

//    modelandview 사용해서 상세페이지 출력 댓글출력x
//    //게시글상세보기
//    @RequestMapping(value = "board_detail")
//    public ModelAndView read(@RequestParam("board_pk") int board_pk, HttpSession session, Boardvo boardvo, ModelAndView mav,Model model, Integer com_pk)
//    {
//        session.setAttribute("board_p", boardvo.getBoard_pk());
//        System.out.println(board_pk);
//        //게시글 조회수 증가
//        userdao.board_check(boardvo);
//
//        mav.setViewName("board_detail");
//        mav.addObject("board_number", userservice.read(board_pk));
//        System.out.println("-컨트롤러영역- 게시글상세페이지");
//        //System.out.println(mav.toString());
//
//        List<Comvo>view_com = userservice.view_com(com_pk);
//
//        mav.addObject("com",userservice.view_com(com_pk));
//
//        //model.addAttribute("com", userservice.view_com(com_pk));
//
//        return mav;
//    }

    //상세페이지 댓글입력
    @RequestMapping(value = "com_register", method = RequestMethod.POST)
    public String comments(Comvo comvo, HttpSession session, Model model, Boardvo boardvo)
    {
        //게시글 pk 세션에 담아서 넣기
        comvo.setCom_pk((Integer)session.getAttribute("board_p"));
        comvo.setCom_user(session.getAttribute("user_id").toString());

        // 댓글 아이디 넘김
        model.addAttribute("user_id", session.getAttribute("user_id"));

        System.out.println("댓글체크 게시글");
        System.out.println(comvo.getCom_pk());
        System.out.println("댓글체크 아이디");

        //게시글 작성자 확인하는 작업 게시판 번호와, 작성자 아이디를 받아오기
        boardvo.setBoard_pk((Integer) session.getAttribute("board_p"));
        boardvo.setId(session.getAttribute("user_id").toString());

        //게시글 작성자 댓글제한
        Boardvo check_board = userservice.check_id(boardvo);
        //댓글 카운트 체크하하는 int count
        int count = userservice.check_cnt(comvo);

        System.out.println("게시글 작성자 확인");
        System.out.println(session.getAttribute("user_id"));
        System.out.println("비교값확인");
        System.out.println(check_board);

        if(check_board == null)
        {
            if (count < 5) {
                //댓글등록
                userservice.comments(comvo);
                userservice.com_cnt(comvo);

                System.out.println("댓글등록시 댓글구분");
                System.out.println(comvo.getCom_num());

                System.out.println("값 테스트");
                System.out.println(comvo.getCom_num());
                session.setAttribute("com_num", comvo.getCom_num());

                List<Boardvo> board_list = userdao.board_list();
                model.addAttribute("board_list", board_list);
                return "board_list";
            }
            else
                {
                    System.out.println("등록가능한 댓글초과");
                    return "success";
                }
        }
        else
            {
                System.out.println("해당게시글 작성자는 댓글을 달수없습니다");
                return "success";
            }
    }
    //댓글삭제
    @RequestMapping(value = "com_remove", method = RequestMethod.POST)
    public String com_del(Comvo comvo, HttpSession session, Model model)
    {
        System.out.println("@@@@@@@@@삭제@@@@@@@@@");
        //comvo.setCom_user(session.getAttribute("user_id").toString());

        System.out.println("@@@@@@@@@@삭제댓글 식별");
        System.out.println(comvo.getCom_num());

        System.out.println("@@@@@@@@@@삭제아이디 식별");
        System.out.println(comvo.getCom_user());

        System.out.println("@@@@@@@@@@세션아이디 식별");
        System.out.println(session.getAttribute("user_id").toString());

        //if(session.getAttribute("user_id").toString()==comvo.getCom_user())
        if(session.getAttribute("user_id").toString().equals(comvo.getCom_user()))
        {
            System.out.println("댓글삭제완료");
            System.out.println(comvo.getCom_num());
            userservice.com_remove(comvo);

            List<Boardvo> board_list = userdao.board_list();
            model.addAttribute("board_list", board_list);
            return "board_list";
        }
        else
        {
            System.out.println("작성자만 삭제할수있습니다");
            return "success";
        }
    }


    //회원가입페이지
    @RequestMapping(value = "singup", method = RequestMethod.GET)
    public String singup_page()
    {
        System.out.println("회원가입 페이지");
        return "singup";
    }

    //회원가입후 로그인페이지 이동
    @RequestMapping(value = "singup", method = RequestMethod.POST)
    public String Singup(Uservo uservo)
    {
        System.out.println(uservo.getId());
        //유저 아이디 비교
        Uservo id_check = userdao.id_check(uservo);

//      userdao.usersingup(uservo);

        if(id_check == null)
        {
            System.out.println("중복체크 : 가입 가능한 아이디 입니다.");
            userdao.usersingup(uservo);
            return "login";

//            if()
//            {
//                System.out.println("회원가입 완료");
//                return "login";
//            }
//            else
//            {
//                System.out.println("회원가입 실패");
//                return "singup";
//
//            }
        }
        else
        {
            System.out.println("회원가입실패");
            System.out.println("중복체크 : 중복된 아이디입니다");
            return "singup";
        }
    }

    //로그인페이지
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login_page()
    {
        System.out.println("로그인대기");
        return "login";
    }



    //로그인 시도
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login_attempt(Uservo uservo, HttpSession session)
    {
        System.out.println("로그인시도중");
        //아이디 중복체크
        Uservo login = userdao.login(uservo);
        if(login == null)
        {
            System.out.println("로그인실패");
            return "fail";
        }
        else
        {
            System.out.println("로그인성공");
            //세션 키값 저장
            session.setAttribute("user_id",uservo.getId());
            System.out.println(uservo.getId());
            return "success";
        }
    }

    //로그인성공 페이지
    @RequestMapping(value = "success", method = RequestMethod.GET)
    public String success_page()
    {
        System.out.println("로그인성공");
        return "success";
    }

    //로그인실패 페이지
    @RequestMapping(value = "fail", method = RequestMethod.GET)
    public String fail_page()
    {
        System.out.println("로그인실패");
        return "fail";
    }

    //친구추가 리스트 페이지
    @RequestMapping(value = "friend_list", method = RequestMethod.GET)
    public String friend()
    {
        System.out.println("Get 친구추가리스트");
        return "friend_list";
    }

    //친구추천 리스트
    //추천 리스트 로그인 당사자 아이디 제외해야함 세션에 담아서 비교????
    @RequestMapping(value = "friend_list", method = RequestMethod.POST)
    public String friendlist(Model model)
    {
        List<Uservo> friendlist = userdao.friendlist();
        System.out.println("POST 친구추가리스트");
        model.addAttribute("friendlist",friendlist);
        return "friend_list";
    }

    //친구추가하기
    // ++ 친구리스트와 입력한 친구같은 비교해서 동일한 것만 등록이 가능하게끔 로직을 추가해야함
    @RequestMapping(value = "frined_add")
    public String frindadd(Friendvo friendvo, HttpSession session)
    {
        Friendvo friend = userservice.friendcheck(friendvo);
        //로그인 세션을 받아와서 리스트에서 해당 세션을 제외한 나머지 리스트를 출력해야함
        //friendvo.setFriend_id(session.getAttribute("user_id").toString());
        //System.out.println("값 찍어보기");
        //System.out.println(friendvo.getFriend_id());

        if(friend == null)
        {
            System.out.println("친구추가 성공");
            System.out.println(friendvo.getFriend_id());
            userdao.friendadd(friendvo);
            return "friend_list";
        }
        else
        {
            System.out.println("잘못된 입력입니다 다시 확인해주세요.");
        }
        return "friend_list";
    }

    //친구리스트확인
    @RequestMapping(value = "list_result")
    public String list_result(Model model, HttpSession session)
    {
        List<Friendvo> result = userdao.list_result();
        model.addAttribute("result",result);
        System.out.println(result);
        return "friend_list";
    }

    //좋아요 체크하기
    @RequestMapping(value = "like_success", method = RequestMethod.POST)
    public String like_wait(Likevo likevo, Boardvo boardvo, HttpSession session)
    {
        //게시판 pk설정
        boardvo.setBoard_pk((Integer)session.getAttribute("board_p"));
        System.out.println(likevo.getBoard_id());

        //게시판 pk 저장
        System.out.println("board_id_value");
        likevo.setBoard_id((Integer)session.getAttribute("board_p"));
        System.out.println("@@@@@@@@@  =>  board_number : "+likevo.getBoard_id());

        //유저 pk 저장
        System.out.println("like_id_value");
        likevo.setLike_id(session.getAttribute("user_id").toString());
        System.out.println("@@@@@@@@@  =>  user_id : "+likevo.getLike_id());

        //게시글 추천판단여부 체크
        Likevo check = userdao.select_like(likevo);

        if(check == null)
        {
            System.out.println("추천완료");
            //카운트 증가
            userdao.like_update(boardvo);
            //유저,게시 pk저장
            userdao.input_like(likevo);
            return "like_success";
        }
        else
        {
            //카운트 감소
            userdao.like_cancel(boardvo);
            //유저,게시 pk삭제
            userdao.like_remove(likevo);
            System.out.println("이미 추천하였습니다");
            return "success";
        }
    }







}






