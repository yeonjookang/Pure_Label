package project.purelabel.cosmetic.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.purelabel.cosmetic.application.response.CosmeticAnalyzeResponseDto;
import project.purelabel.cosmetic.application.response.CosmeticInfoResponseDto;
import project.purelabel.cosmetic.application.response.CosmeticListResponseDto;
import project.purelabel.cosmetic.domain.CosmeticRepository;
import project.purelabel.cosmetic.domain.entity.Cosmetic;
import project.purelabel.cosmetic.exception.CosmeticException;
import project.purelabel.cosmetic.exception.errorcode.CosmeticErrorCode;
import project.purelabel.ingredient.domain.IngredientRepository;
import project.purelabel.ingredient.domain.entity.Ingredient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        if (search == null || search.isEmpty()) cosmetics = cosmeticRepository.findAll(pageRequest);
        else cosmetics = cosmeticRepository.findAllWithSearch(search, pageRequest);

        return getCosmeticListResponse(cosmetics);
    }

    private List<CosmeticListResponseDto> getCosmeticListResponse(Page<Cosmetic> cosmetics) {
        List<CosmeticListResponseDto> response = new ArrayList<>();
        for (int i = 0; i < cosmetics.getContent().size(); i++) {
            CosmeticListResponseDto cosmeticListResponseDto = new CosmeticListResponseDto(cosmetics.getContent().get(i));
            response.add(cosmeticListResponseDto);
        }
        return response;
    }

    public CosmeticInfoResponseDto getCosmeticInfo(Long cosmeticId) {
        Optional<Cosmetic> cosmetic = cosmeticRepository.findById(cosmeticId);
        if (!cosmetic.isPresent()) throw new CosmeticException(CosmeticErrorCode.NO_COSMETIC);

        // IngredientId 리스트를 이용해 실제 Ingredient name 리스트를 조회
        List<String> ingredientNames = ingredientRepository.findByPkIn(cosmetic.get().getIngredientsPks()).stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());

        return CosmeticInfoResponseDto.builder()
                .cosmetic(cosmetic.get())
                .ingredients(ingredientNames)
                .build();
    }

    public CosmeticAnalyzeResponseDto analyzeCosmeticImage(MultipartFile image) {
        saveImage(image);
        runPythonScript();
        List<String> extractedIngredients = readExtractedIngredientsFromTextFile();
        List<Ingredient> matchingIngredients = new ArrayList<>();
        for (String ingredientName : extractedIngredients) {
            ingredientRepository.findByName(ingredientName).ifPresent(matchingIngredients::add);
        }
        return new CosmeticAnalyzeResponseDto(matchingIngredients);
    }

    public CosmeticAnalyzeResponseDto analyzeCosmeticText(String content) {
        List<String> extractedIngredients = readExtractedIngredientsFromText(content);
        List<Ingredient> matchingIngredients = new ArrayList<>();

        for (String ingredientName : extractedIngredients) {
            ingredientRepository.findByName(ingredientName).ifPresent(matchingIngredients::add);
        }

        return new CosmeticAnalyzeResponseDto(matchingIngredients);
    }

    private List<String> readExtractedIngredientsFromText(String content) {
        List<String> ingredients = new ArrayList<>();
        String[] splitIngredients = content.split("[,\\s]+");

        for (String ingredient : splitIngredients) {
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient.trim());
            }
        }
        return ingredients;
    }

    private void saveImage(MultipartFile image) {
        try {
            // 파일 이름과 확장자 가져오기
            String originalFilename = image.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf('.')) : ".png"; // 기본 확장자를 .png로 설정

            // 저장할 파일 경로 설정
            File file = new File("/home/server/image/image" + extension);
            // 파일을 지정한 경로에 저장
            image.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runPythonScript() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python3", "/home/server/ML/ocr_inference.py");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<String> readExtractedIngredientsFromTextFile() {
        List<String> ingredients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/server/ML/result.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 줄을 콤마와 공백으로 나누고, trim()으로 공백 제거 후 리스트에 추가
                String[] splitIngredients = line.split("[,\\s]+");
                System.out.println("test ^^: "+ splitIngredients.length);
                for (String ingredient : splitIngredients) {
                    System.out.println("test: "+ingredient);
                    if (!ingredient.isEmpty()) { // 빈 문자열은 추가하지 않음
                        ingredients.add(ingredient.trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingredients;
    }
}
