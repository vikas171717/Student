package com.asmadiya.student.service;

import com.asmadiya.student.dto.ExternalCommentsDto;

public interface ExternalCommentService {
    String sendCommentData(String dynamicPath, ExternalCommentsDto externalCommentsDto);

    String fetchCommentData(String query);

    String updateComment(int id, ExternalCommentsDto externalCommentsDto);

    String deleteCommentById(int id);

    String getCommentById(int id);
}
