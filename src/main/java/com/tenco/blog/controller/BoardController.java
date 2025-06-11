package com.tenco.blog.controller;

import com.tenco.blog.model.Board;
import com.tenco.blog.repository.BoardNativeRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    // 의존성 주입: 스프링이 BoardNativeRepository 객체를 자동으로 주입
    private final BoardNativeRepository boardNativeRepository;

    // @PostMapping: HTTP POST 요청을 처리
    // 폼에서 제출된 데이터를 받아서 처리
    @PostMapping("/board/save")
    public String save(String title, String content, String username){
        // 폼의 name 속성과 매개변수명이 일치하면 자동으로 값이 바인딩됨
        // name="title" → String title로 자동 매핑

        // Repository를 통해 데이터베이스에 저장
        boardNativeRepository.save(title, content, username);

        // redirect: 저장 후 메인 페이지로 이동
        // POST 요청 후 redirect로 PRG(Post-Redirect-Get) 패턴 구현
        return "redirect:/";
    }


    // 메인 페이지: 게시글 목록 보기
    // Model 클래스 대신 HttpServletRequest 사용해 보기
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        // Repository에서 모든 게시글 조회
        List<Board> boardList = boardNativeRepository.findAll();

        // HttpServletRequest를 사용해서 뷰에 데이터 전달
        // "boardList"라는 이름으로 템플릿에서 사용 가능
        // Model 객체를 사용하는 방법도 있음: Model model → model.addAttribute()
        request.setAttribute("boardList", boardList);

        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        // 게시글 작성 폼을 보여주는 뷰 반환
        // templates/board/save-form.html 파일을 렌더링
        return "board/save-form";
    }

    // 프로젝트 내에서 동일한 URL 매핑을 설정하면 오류 발생 (동일한 주소 설계)
    // 게시글 상세보기: PathVariable을 사용한 동적 URL 처리
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Integer id, HttpServletRequest request) {

        // Integer 타입으로 자동 변환됨 (Spring의 타입 컨버전)
        // 숫자가 아닌 값이 들어오면 400 Bad Request 에러 발생

        // Repository에서 해당 ID의 게시글 조회
        Board board = boardNativeRepository.findById(id);

        // 조회된 게시글을 뷰에 전달
        // "board"라는 이름으로 템플릿에서 사용 가능
        request.setAttribute("board", board);

        // board 폴더의 detail.html 템플릿 렌더링
        return "board/detail";
    }

}

