package project.purelabel.cosmetic.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.purelabel.cosmetic.application.CosmeticService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.purelabel.global.exception.errorcode.BaseExceptionErrorCode.BAD_REQUEST;

@WebMvcTest(controllers = CosmeticController.class)
public class CosmeticControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CosmeticService cosmeticService;

    /**
     * 요청: 리뷰순(default), 성분순
     *  +) page(default=0), size(default=3)
     *  +) 검색기능
     */
    @DisplayName("화장품 랭킹 조회 시 기본 값 적용 - 리뷰순, page=0, size=3")
    @Test
    void 화장품_랭킹조회_기본값() throws Exception {
        // when
        mockMvc.perform(get("/cosmetic/list"))
                .andExpect(status().isOk());

        // then: 기본값으로 서비스 메서드가 호출되었는지 검증
        verify(cosmeticService).getCosmeticList(null,"rating", 0, 3);
    }

    @DisplayName("정렬 파라미터에 grade,rating을 제외한 값이 들어오면 예외가 발생한다.")
    @Test
    void 정렬_파라미터에_이상한값_예외() throws Exception {
        //given
        String wierdSort = "wierd_sort";

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/cosmetic/list")
                        .param("sort", wierdSort)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value("Failed to convert value of type 'java.lang.String' to " +
                        "required type 'project.purelabel.cosmetic.api.SortEnum'; Failed to convert from type " +
                        "[java.lang.String] to type [@org.springframework.web.bind.annotation.RequestParam " +
                        "project.purelabel.cosmetic.api.SortEnum] for value [wierd_sort]"));
    }

}
