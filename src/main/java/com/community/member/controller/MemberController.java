package com.community.member.controller;

import com.community.member.controller.dto.MemberSignUpRequest;
import com.community.member.service.MemberService;
import com.community.member.service.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberSignUpRequest request) {
        MemberDto response = memberService.create(request);
        URI uri = URI.create(String.valueOf(response.id()));
        return ResponseEntity
                .created(uri)
                .body(response);
    }
}
