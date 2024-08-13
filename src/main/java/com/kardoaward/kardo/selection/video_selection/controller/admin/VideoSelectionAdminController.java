package com.kardoaward.kardo.selection.video_selection.controller.admin;

import com.kardoaward.kardo.selection.video_selection.model.UpdateVideoSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.NewVideoSelectionRequest;
import com.kardoaward.kardo.selection.video_selection.model.dto.VideoSelectionDto;
import com.kardoaward.kardo.selection.video_selection.service.VideoSelectionService;
import com.kardoaward.kardo.selection.video_selection.service.helper.VideoSelectionValidationHelper;
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
@RequestMapping("/admin/selections/video")
@Validated
@Tag(name="Видео-отбор: Admin.", description="API администратора для работы с видео-отборами.")
public class VideoSelectionAdminController {

    private final VideoSelectionService videoSelectionService;

    private final VideoSelectionValidationHelper videoSelectionValidationHelper;

    @Operation(summary = "Добавление видео-отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-отбор добавлен.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoSelectionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Гранд-соревнование не найдено", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PostMapping
    @Secured("ADMIN")
    public VideoSelectionDto createVideoSelection(@Parameter(description = "Данные добавляемого видео-отбора")
                                                  @RequestBody @Valid
                                                  NewVideoSelectionRequest newVideoSelectionRequest) {
        log.info("Добавление администратором нового видео-отбора {}.", newVideoSelectionRequest);
        videoSelectionValidationHelper.isNewVideoSelectionDateValid(newVideoSelectionRequest);
        return videoSelectionService.addVideoSelection(newVideoSelectionRequest);
    }

    @Operation(summary = "Удаление видео-отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-отбор удалён.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @DeleteMapping("/{selectionId}")
    @Secured("ADMIN")
    public void deleteVideoSelection(@Parameter(description = "id видео-отбора")
                                     @PathVariable @Positive Long selectionId) {
        log.info("Удаление администратором видео-отбора с ИД {}.", selectionId);
        videoSelectionService.deleteVideoSelection(selectionId);
    }

    @Operation(summary = "Обновление видео-отбора.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Видео-отбор обновлён.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VideoSelectionDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Видео-отбор не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)})
    @PatchMapping("/{selectionId}")
    @Secured("ADMIN")
    public VideoSelectionDto updateVideoSelectionById(@Parameter(description = "id видео-отбора")
                                                      @PathVariable @Positive Long selectionId,
                                                      @Parameter(description = "Данные обновляемого видео-отбора")
                                                      @RequestBody @Valid UpdateVideoSelectionRequest request) {
        log.info("Обновление администратором видео-отбора с ИД {}.", selectionId);
        return videoSelectionService.updateVideoSelectionById(selectionId, request);
    }
}
