package com.tenco.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

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

