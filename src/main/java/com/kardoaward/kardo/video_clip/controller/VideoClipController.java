package com.kardoaward.kardo.video_clip.controller;

import com.google.gson.Gson;
import com.kardoaward.kardo.security.UserDetailsImpl;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.dto.NewVideoClipRequest;
import com.kardoaward.kardo.video_clip.dto.UpdateVideoClipRequest;
import com.kardoaward.kardo.video_clip.dto.VideoClipDto;
import com.kardoaward.kardo.video_clip.service.VideoClipService;
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
import jakarta.validation.constraints.Size;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/videos")
@Validated
@Tag(name="Видео-клип.", description="API для работы с видео-клипами для зарегистрированных пользователей.")
public class VideoClipController {

    private final VideoClipService videoClipService;

    @Operation(summary = "Добавление видео-клипа.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-клип добавлен.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoClipDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping
    @Secured("USER")
    public VideoClipDto createVideoClip(@Parameter(description = "список хештегов в формате json")
                                            @RequestParam("text") String json,
                                        @Parameter(description = "MultipartFile файл с видео-клипом")
                                            @RequestParam("video") MultipartFile file) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Добавление пользователем с ИД {} нового видео-клипа.", requestor.getId());
        /* ToDo
            Разобраться как принимать составные запросы.
         */
        NewVideoClipRequest request = new Gson().fromJson(json, NewVideoClipRequest.class);
        return videoClipService.addVideoClip(requestor, request, file);
    }

    @Operation(summary = "Удаление видео-клипа.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-клип удален.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-клип не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{videoId}")
    @Secured({"ADMIN", "USER"})
    public void deleteVideoClipById(@Parameter(description = "id видео-клипа")
                                        @PathVariable @Positive Long videoId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Удаление видео-клипа с ИД {}.", videoId);
        videoClipService.deleteVideoClipById(requestor, videoId);
    }

    @Operation(summary = "Получение видео-клипа.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-клип найден.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoClipDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-клип не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/{videoId}")
    @Secured({"ADMIN", "USER"})
    public VideoClipDto getVideoClipById(@Parameter(description = "id видео-клипа")
                                             @PathVariable @Positive Long videoId) {
        log.debug("Возвращение видео-клипа с ИД {}.", videoId);
        return videoClipService.getVideoClipById(videoId);
    }

    @Operation(summary = "Обновление видео-клипа.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-клип обновлён.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoClipDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-клип не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/{videoId}")
    @Secured("USER")
    public VideoClipDto updateVideoClipById(@Parameter(description = "id видео-клипа")
                                                @PathVariable @Positive Long videoId,
                                            @Parameter(description = "Данные обновляемого видео-клипа")
                                                @RequestBody @Valid UpdateVideoClipRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Обновление пользователем с ИД {} видео-клипа с ИД {}.", requestor.getId(), videoId);
        return videoClipService.updateVideoClipById(requestor, videoId, request);
    }

    @Operation(summary = "Получение списка видео-клипов по хештегу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-клип не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @GetMapping("/search")
    @Secured({"ADMIN", "USER"})
    public List<VideoClipDto> getVideoClipsByHashtag(@Parameter(description = "хештег")
                                                         @RequestParam @Size(min = 2, max = 20) String hashtag,
                                                     @Parameter(description = "Количество элементов, которые " +
                                                             "нужно пропустить для формирования текущего набора")
                                                         @RequestParam(defaultValue = "0") @Min(0) int from,
                                                     @Parameter(description = "Количество элементов в наборе")
                                                         @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Возвращение списка видео-клипов с хештегом {}.", hashtag);
        return videoClipService.getVideoClipsByHashtag(hashtag, from, size);
    }

    @Operation(summary = "Добавление лайка к видео-клипу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Лайк добавлен.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoClipDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-клип не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping("/{videoId}/likes")
    @Secured({"ADMIN", "USER"})
    public VideoClipDto addLikeByVideoClipId(@Parameter(description = "id видео-клипа")
                                                 @PathVariable @Positive Long videoId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User requestor = userDetails.getUser();
        log.info("Добавление пользователем с ИД {} лайка к видео-клипу с ИД {}.", requestor.getId(), videoId);
        return videoClipService.addLikeByVideoClipId(requestor, videoId);
    }

    @Operation(summary = "Удаление лайка к видео-клипу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Лайк удалён.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoClipDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-клип не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{videoId}/likes")
    @Secured({"ADMIN", "USER"})
    public VideoClipDto deleteLikeByVideoClipId(@Parameter(description = "id видео-клипа")
                                                    @PathVariable @Positive Long videoId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long requestorId = userDetails.getUser().getId();
        log.info("Удаление пользователем с ИД {} своего лайка к видео-клипу с ИД {}.", requestorId, videoId);
        return videoClipService.deleteLikeByVideoClipId(requestorId, videoId);
    }
}
