package project.purelabel.cosmetic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.purelabel.cosmetic.application.response.CosmeticInfoResponseDto;
import project.purelabel.cosmetic.application.response.CosmeticListResponseDto;
import project.purelabel.cosmetic.domain.CosmeticRepository;
import project.purelabel.cosmetic.domain.entity.Cosmetic;
import project.purelabel.cosmetic.exception.CosmeticException;
import project.purelabel.cosmetic.exception.errorcode.CosmeticErrorCode;
import project.purelabel.ingredient.domain.IngredientRepository;
import project.purelabel.cosmetic.domain.value.IngredientPk;
import project.purelabel.ingredient.domain.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CosmeticService {
    private final CosmeticRepository cosmeticRepository;
    private final IngredientRepository ingredientRepository;
    public List<CosmeticListResponseDto> getCosmeticList(String search, String sort, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<Cosmetic> cosmetics;

        if(search==null || search.isEmpty()) cosmetics = cosmeticRepository.findAll(pageRequest);
        else cosmetics = cosmeticRepository.findAllWithSearch(search,pageRequest);

        return getCosmeticListResponse(cosmetics);
    }

    private List<CosmeticListResponseDto> getCosmeticListResponse(Page<Cosmetic> cosmetics) {
        List<CosmeticListResponseDto> response = new ArrayList<>();
        for(int i=0;i< cosmetics.getContent().size();i++){
            CosmeticListResponseDto cosmeticListResponseDto = new CosmeticListResponseDto(cosmetics.getContent().get(i));
            response.add(cosmeticListResponseDto);
        }
        return response;
    }

    public CosmeticInfoResponseDto getCosmeticInfo(Long cosmeticId) {
        Optional<Cosmetic> cosmetic = cosmeticRepository.findById(cosmeticId);
        if(!cosmetic.isPresent()) throw new CosmeticException(CosmeticErrorCode.NO_COSMETIC);

        // IngredientId 리스트를 이용해 실제 Ingredient name 리스트를 조회
        List<String> ingredientNames = ingredientRepository.findByPkIn(cosmetic.get().getIngredientsPks()).stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());

        return CosmeticInfoResponseDto.builder()
                .cosmetic(cosmetic.get())
                .ingredients(ingredientNames)
                .build();
    }
}
