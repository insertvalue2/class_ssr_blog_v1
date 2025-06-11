package com.tenco.blog.controller;

import com.tenco.blog.repository.BoardNativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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


    // @GetMapping에 배열로 여러 경로를 지정 가능
    // "/" 또는 "/index" 둘 다 같은 메서드를 실행함
    // 메인 페이지는 보통 여러 경로로 접근 가능하게 설정
    @GetMapping({"/", "/index"} )
    public String index() {
        // 메인 페이지(홈페이지)를 보여주는 뷰 반환
        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        // 게시글 작성 폼을 보여주는 뷰 반환
        // templates/board/save-form.html 파일을 렌더링
        return "board/save-form";
    }

    // @PathVariable: URL 경로의 일부를 변수로 받아오는 어노테이션
    // {id} 부분이 실제 숫자로 치환되어 id 매개변수로 전달됨
    // 예: /board/1 → id=1, /board/100 → id=100
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id) {
        // URL에서 받은 id 값을 사용해서 특정 게시글 상세보기
        // 실제로는 이 id로 데이터베이스에서 게시글을 조회해야 함
        return "board/detail";
    }


}

