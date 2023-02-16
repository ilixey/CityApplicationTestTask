package vaspiakou.citylistapplication.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vaspiakou.citylistapplication.dto.request.CityDto;
import vaspiakou.citylistapplication.exception.FileErrorException;
import vaspiakou.citylistapplication.model.City;
import vaspiakou.citylistapplication.util.CityUtil;

@ContextConfiguration(classes = {CityService.class})
@ExtendWith(SpringExtension.class)
class CityServiceTest {
    @Autowired
    private CityService cityService;

    @MockBean
    private CityUtil cityUtil;
    private final String TEST_STRING = "test";


    @Test
    void findCityByNameTest() {
        City city = new City();
        city.setId(123L);
        city.setName(TEST_STRING);
        city.setPhoto(TEST_STRING);
        when(cityUtil.findCityByName(any())).thenReturn(city);
        assertSame(city, cityService.findCityByName(TEST_STRING));
        verify(cityUtil).findCityByName(any());
    }

    @Test
    void getPageOfCitiesTest() {
        ArrayList<City> cityList = new ArrayList<>();
        when(cityUtil.getCities(anyInt())).thenReturn(cityList);
        List<City> actualPageOfCities = cityService.getPageOfCities(1);
        assertSame(cityList, actualPageOfCities);
        assertTrue(actualPageOfCities.isEmpty());
        verify(cityUtil).getCities(anyInt());
    }

    @Test
    void editCityTest() {
        City city = new City();
        city.setId(123L);
        city.setName(TEST_STRING);
        city.setPhoto(TEST_STRING);
        when(cityUtil.findCityById(any())).thenReturn(city);
        doNothing().when(cityUtil).editCity(any(), any());
        CityDto cityDto = mock(CityDto.class);
        when(cityDto.getId()).thenReturn(123L);
        assertSame(city, cityService.editCity(cityDto));
        verify(cityUtil).findCityById(any());
        verify(cityUtil).editCity(any(), any());
        verify(cityDto).getId();
    }

    @Test
    void resolveFileTest() throws IOException {
        doNothing().when(cityUtil).saveCities(any());
        when(cityUtil.hasCSVFormat(any())).thenReturn(true);
        cityService.resolveFile(new MockMultipartFile(TEST_STRING, new ByteArrayInputStream(TEST_STRING.getBytes("UTF-8"))));
        verify(cityUtil).hasCSVFormat(any());
        verify(cityUtil).saveCities(any());
    }

    @Test
    void resolveFileTestShouldThrowException() {
        doNothing().when(cityUtil).saveCities(any());
        when(cityUtil.hasCSVFormat(any())).thenReturn(false);
        assertThrows(FileErrorException.class, () -> cityService
                .resolveFile(new MockMultipartFile(TEST_STRING, new ByteArrayInputStream(TEST_STRING.getBytes("UTF-8")))));
        verify(cityUtil).hasCSVFormat(any());
    }

}

