package com.kardoaward.kardo.comment.controller;

import com.kardoaward.kardo.comment.dto.CommentDto;
import com.kardoaward.kardo.comment.dto.NewCommentRequest;
import com.kardoaward.kardo.comment.dto.UpdateCommentRequest;
import com.kardoaward.kardo.comment.service.CommentService;
import com.kardoaward.kardo.comment.service.helper.CommentValidationHelper;
import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/comments")
@Validated
@Tag(name="Комментарии.", description="Публичный API для работы с комментариями.")
public class CommentController {

    private final CommentService commentService;

    private final CommentValidationHelper commentValidationHelper;

    @Operation(summary = "Добавление нового комментария к видео-клипу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий добавлен.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-клип не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping("/videos/{videoId}")
    @Secured({"ADMIN", "USER"})
    public CommentDto createComment(@Parameter(description = "id видео-клипа")
                                    @PathVariable @Positive Long videoId,
                                    @Parameter(description = "Данные добавляемого комментария")
                                    @RequestBody @Valid NewCommentRequest newCommentRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Добавление комментария к видео-клипу с ИД {}.", videoId);
        commentValidationHelper.isUserAuthor(requestor, newCommentRequest.getAuthorId());
        return commentService.addComment(requestor, videoId, newCommentRequest);
    }

    @Operation(summary = "Удаление комментария к видео-клипу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий удалён.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{commentId}")
    @Secured({"ADMIN", "USER"})
    public void deleteCommentById(@Parameter(description = "id комментария")
                                  @PathVariable @Positive Long commentId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление комментария с ИД {}.", commentId);
        commentService.deleteCommentById(requestor, commentId);
    }

    @Operation(summary = "Получение комментария к видео-клипу по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий найден.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{commentId}")
    @Secured({"ADMIN", "USER"})
    public CommentDto getCommentById(@Parameter(description = "id комментария")
                                     @PathVariable @Positive Long commentId) {
        log.info("Возвращение комментария с ИД {}.", commentId);
        return commentService.getCommentById(commentId);
    }

    @Operation(summary = "Обновление пользователем своего комментария к видео-клипу по ИД.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий обновлён.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/{commentId}")
    @Secured({"ADMIN", "USER"})
    public CommentDto updateCommentById(@Parameter(description = "id комментария")
                                        @PathVariable @Positive Long commentId,
                                        @Parameter(description = "Данные обновляемого комментария")
                                        @RequestBody @Valid UpdateCommentRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Обновление пользователем с ИД {} своего комментария с ИД {}.", requestor.getId(), commentId);
        return commentService.updateCommentById(requestor, commentId, request);
    }

    @Operation(summary = "Получение списка комментариев к видео-клипу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-клип не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/videos/{videoId}")
    @Secured({"ADMIN", "USER"})
    public List<CommentDto> getCommentsByVideoId(@Parameter(description = "id видео-клипа")
                                                 @PathVariable @Positive Long videoId,
                                                 @Parameter(description = "Количество элементов, которые нужно " +
                                                         "пропустить для формирования текущего набора")
                                                 @RequestParam(defaultValue = "0") @Min(0) int from,
                                                 @Parameter(description = "Количество элементов в наборе")
                                                 @RequestParam(defaultValue = "10") @Positive int size) {
        return commentService.getCommentsByVideoId(videoId, from, size);
    }
}
