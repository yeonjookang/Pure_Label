package project.purelabel.cosmetic.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import project.purelabel.cosmetic.application.CosmeticService;
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
}
