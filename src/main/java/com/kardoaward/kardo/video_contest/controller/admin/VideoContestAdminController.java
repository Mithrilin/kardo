package com.kardoaward.kardo.video_contest.controller.admin;

import com.kardoaward.kardo.video_contest.model.dto.NewVideoContestRequest;
import com.kardoaward.kardo.video_contest.model.dto.VideoContestDto;
import com.kardoaward.kardo.video_contest.model.dto.UpdateVideoContestRequest;
import com.kardoaward.kardo.video_contest.service.VideoContestService;
import com.kardoaward.kardo.video_contest.service.helper.VideoContestValidationHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/admin/contests")
@Validated
@Tag(name="Видео-соревнование: Admin.", description="API администратора для работы с видео-соревнованиями.")
public class VideoContestAdminController {

    private final VideoContestService service;

    private final VideoContestValidationHelper videoHelper;

    @Operation(summary = "Добавление видео-соревнования.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-соревнование добавлено.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoContestDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping
    @Secured("ADMIN")
    public VideoContestDto createVideoContest(@Parameter(description = "Данные добавляемого видео-соревнования")
                                                  @RequestBody @Valid NewVideoContestRequest newContest) {
        log.info("Добавление администратором нового видео-конкурса {}.", newContest);
        videoHelper.isNewVideoContestDateValid(newContest);
        return service.createVideoContest(newContest);
    }

    @Operation(summary = "Удаление видео-соревнования.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-соревнование удалено.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{contestId}")
    @Secured("ADMIN")
    public void deleteVideoContest(@Parameter(description = "id видео-соревнования")
                                       @PathVariable @Positive Long contestId) {
        log.info("Удаление администратором видео-конкурса с ИД {}.", contestId);
        service.deleteVideoContest(contestId);
    }

    @Operation(summary = "Обновление видео-соревнования.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-соревнование обновлено.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoContestDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/{contestId}")
    @Secured("ADMIN")
    public VideoContestDto updateVideoContest(@Parameter(description = "id видео-соревнования")
                                                  @PathVariable @Positive Long contestId,
                                              @Parameter(description = "Данные обновляемого видео-соревнования")
                                                  @RequestBody @Valid UpdateVideoContestRequest request) {
        log.info("Обновление администратором видео-конкурса с ИД {}.", contestId);
        return service.updateVideoContest(contestId, request);
    }
}