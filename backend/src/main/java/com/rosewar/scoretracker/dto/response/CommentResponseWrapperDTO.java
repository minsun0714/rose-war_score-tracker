package com.rosewar.scoretracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseWrapperDTO {
    private List<CommentResponseDTO> topLevelComments;
    private int totalComments;
}
