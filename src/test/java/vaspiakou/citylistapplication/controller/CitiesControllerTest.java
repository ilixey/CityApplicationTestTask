package vaspiakou.citylistapplication.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import vaspiakou.citylistapplication.advice.GlobalExceptionHandler;
import vaspiakou.citylistapplication.dto.request.CityDto;
import vaspiakou.citylistapplication.model.City;
import vaspiakou.citylistapplication.security.JwtFilter;
import vaspiakou.citylistapplication.security.JwtTokenUtil;
import vaspiakou.citylistapplication.security.JwtUserDetailsService;
import vaspiakou.citylistapplication.security.WebSecurityConfig;
import vaspiakou.citylistapplication.service.CityService;
import vaspiakou.citylistapplication.util.ResponseUtil;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        WebSecurityConfig.class,
        JwtUserDetailsService.class,
        GlobalExceptionHandler.class,
        ResponseUtil.class,
        CitiesController.class,
        CityService.class,
        JwtTokenUtil.class,
        JwtFilter.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(ResponseUtil.class),
        @MockBean(CityService.class),
        @MockBean(JwtUserDetailsService.class),
        @MockBean(JwtTokenUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CitiesControllerTest {

    private final MockMvc mockMvc;
    private final CityService cityService;
    private final String GET_CITY_URL = "/city/Tokyo";
    private final String EDIT_CITY_URL = "/city/editing";
    private final String LOAD_FILE_URL = "/city/load";
    private final String GET_PAGE_OF_CITIES_URL = "/city/list/1";
    private final String TEST_STRING = "test";

    @Test
    @WithMockUser(authorities = "ROLE_ALLOWED_VIEW")
    void getCityByNameWithAuthorizationShouldReturnOkStatus() throws Exception {
        City city = createCity();
        when(cityService.findCityByName(anyString())).thenReturn(city);
        mockMvc.perform(get(GET_CITY_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(cityService, times(1)).findCityByName(anyString());
    }

    @Test
    void getCityByNameWithoutAuthorizationShouldReturnForbiddenStatus() throws Exception {
        mockMvc.perform(get(GET_CITY_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ALLOWED_VIEW")
    void getPageOfCitiesWithAuthorizationShouldReturnOkStatus() throws Exception {
        City city = createCity();
        List list = List.of(city);
        when(cityService.getPageOfCities(anyInt())).thenReturn(list);
        mockMvc.perform(get(GET_PAGE_OF_CITIES_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(cityService, times(1)).getPageOfCities(anyInt());
    }

    @Test
    void getListOfCitiesWithoutAuthorizationShouldReturnForbiddenStatus() throws Exception {
        mockMvc.perform(get(GET_PAGE_OF_CITIES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ALLOWED_VIEW")
    void editCityWithUserAuthorizationShouldReturnForbiddenStatus() throws Exception {
        mockMvc.perform(patch(EDIT_CITY_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ROLE_ALLOWED_EDIT")
    void editCityWithAdminAuthorizationShouldReturnOkStatus() throws Exception {
        CityDto cityDto = createCityDto();
        String json = convertRequestDtoToJson(cityDto);
        City city = createCity();
        when(cityService.editCity(cityDto)).thenReturn(city);
        mockMvc.perform(patch(EDIT_CITY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(cityService, times(1)).editCity(cityDto);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ALLOWED_VIEW")
    void loadFileWithUserAuthorizationShouldReturnForbiddenStatus() throws Exception {
        mockMvc.perform(post(LOAD_FILE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private City createCity() {
        return City.builder()
                .id(1L)
                .name(TEST_STRING)
                .photo(TEST_STRING)
                .build();
    }

    private CityDto createCityDto() {
        return CityDto.builder()
                .id(1L)
                .name(TEST_STRING)
                .photo(TEST_STRING)
                .build();
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}
