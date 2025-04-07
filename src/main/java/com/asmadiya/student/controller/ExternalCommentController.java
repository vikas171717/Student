package com.asmadiya.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.asmadiya.student.dto.ExternalCommentsDto;
import com.asmadiya.student.service.ExternalCommentService;

@RestController
@RequestMapping("api/comments")
public class ExternalCommentController {

    @Autowired
    private ExternalCommentService commentService;

    @PostMapping
    public ResponseEntity<String> postBaseComment(
            @RequestBody ExternalCommentsDto externalCommentsDto) {
        String response = commentService.sendCommentData("", externalCommentsDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{path}")
    public ResponseEntity<String> postCommentWithPath(
            @PathVariable String path,
            @RequestBody ExternalCommentsDto externalCommentsDto) {
        String response = commentService.sendCommentData(path, externalCommentsDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<String> getBaseComments(@RequestParam(required = false) String query) {
        String response = commentService.fetchCommentData(query);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(
            @PathVariable int id,
            @RequestBody ExternalCommentsDto externalCommentsDto) {
        String response = commentService.updateComment(id, externalCommentsDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id) {
        String response = commentService.deleteCommentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCommentById(@PathVariable int id) {
        String response = commentService.getCommentById(id);
        return ResponseEntity.ok(response);
    }

}
