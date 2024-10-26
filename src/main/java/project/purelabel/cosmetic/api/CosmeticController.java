package project.purelabel.cosmetic.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.purelabel.cosmetic.api.request.CosmeticTextAnalyzeRequestDto;
import project.purelabel.cosmetic.application.CosmeticService;
import project.purelabel.cosmetic.application.response.CosmeticAnalyzeResponseDto;
import project.purelabel.cosmetic.application.response.CosmeticInfoResponseDto;
import project.purelabel.cosmetic.application.response.CosmeticListResponseDto;
import project.purelabel.global.response.BaseResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cosmetic")
public class CosmeticController {
    private final CosmeticService cosmeticService;
    @GetMapping("/list")
    public BaseResponse<List<CosmeticListResponseDto>> getCosmeticList(
            @RequestParam(value = "sort", defaultValue = "rating") SortEnum sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "search", required = false) String search) {
        List<CosmeticListResponseDto> cosmeticListResponse = cosmeticService.getCosmeticList(search,sort.name(),page,size);
        return new BaseResponse<>(cosmeticListResponse);
    }

    @GetMapping("/{cosmeticId}")
    public BaseResponse<CosmeticInfoResponseDto> getCosmeticInfo(@PathVariable Long cosmeticId) {
        CosmeticInfoResponseDto cosmeticInfoResponse = cosmeticService.getCosmeticInfo(cosmeticId);
        return new BaseResponse<>(cosmeticInfoResponse);
    }

    @GetMapping("/image/analyze")
    public BaseResponse<CosmeticAnalyzeResponseDto> analyzeCosmeticImage(
            @RequestPart(value = "imageFile")MultipartFile image
            ){
        CosmeticAnalyzeResponseDto response = cosmeticService.analyzeCosmeticImage(image);
        return new BaseResponse<>(response);
    }
    @GetMapping("/text/analyze")
    public BaseResponse<CosmeticAnalyzeResponseDto> analyzeCosmeticText(@Validated @RequestBody CosmeticTextAnalyzeRequestDto requestDto){
        CosmeticAnalyzeResponseDto response = cosmeticService.analyzeCosmeticText(requestDto.getContent());
        return new BaseResponse<>(response);
    }
}
