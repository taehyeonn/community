package com.community.member.controller;

import com.community.member.controller.dto.MemberCreateRequest;
import com.community.member.service.MemberService;
import com.community.member.service.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberCreateRequest request) {
        MemberDto response = memberService.create(request);
        URI uri = URI.create(String.valueOf(response.id()));
        return ResponseEntity
                .created(uri)
                .body(response);
    }

    @GetMapping("/test") //todo: 테스트용 임시 메서드
    public ResponseEntity<Object> test() {
        System.out.println("zz");
        return ResponseEntity.ok().build();
    }
}
