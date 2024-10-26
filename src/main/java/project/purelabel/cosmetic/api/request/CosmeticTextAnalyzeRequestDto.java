package project.purelabel.cosmetic.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CosmeticTextAnalyzeRequestDto {
    @NotNull
    private String content;
}
