package project.purelabel.cosmetic.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.purelabel.cosmetic.domain.entity.Cosmetic;

import java.util.List;

@SpringBootTest
public class CosmeticRepositoryTest {
    @Autowired
    private CosmeticRepository cosmeticRepository;
    @Test
    @DisplayName("리뷰순으로 0페이지 3개의 데이터를 조회한다.")
    void 검색없이_리뷰순으로_페이지네이션(){
        //given
        int page = 0;
        int size = 3;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("rating").descending());

        //when
        Page<Cosmetic> cosmeticsResponse = cosmeticRepository.findAll(pageRequest);
        List<Cosmetic> cosmetics = cosmeticsResponse.getContent();

        //then
        Assertions.assertThat(cosmetics).hasSize(3)
                .extracting("name")
                .containsExactly("별점 5","별점 4","별점 3");
    }
    @Test
    @DisplayName("성분순으로 0페이지 3개의 데이터를 조회한다.")
    void 검색없이_성분순으로_페이지네이션(){
        //given
        int page = 0;
        int size = 3;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("grade").descending());

        //when
        Page<Cosmetic> cosmeticsResponse = cosmeticRepository.findAll(pageRequest);
        List<Cosmetic> cosmetics = cosmeticsResponse.getContent();

        //then
        Assertions.assertThat(cosmetics).hasSize(3)
                .extracting("name")
                .containsExactly("검색 2","별점 4","별점 3");
    }
    @Test
    @DisplayName("검색이 들어간 화장품 중, 리뷰순으로 0페이지 1개의 데이터를 조회한다.")
    void 검색기능과_리뷰순으로_페이지네이션(){
        //given
        String search = "검색";
        int page = 0;
        int size = 1;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("rating").descending());

        //when
        Page<Cosmetic> cosmeticsResponse = cosmeticRepository.findAllWithSearch(search,pageRequest);
        List<Cosmetic> cosmetics = cosmeticsResponse.getContent();

        //then
        Assertions.assertThat(cosmetics).hasSize(1)
                .extracting("name")
                .containsExactly("검색 2");
    }
}
