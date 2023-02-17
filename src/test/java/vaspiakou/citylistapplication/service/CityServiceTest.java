package vaspiakou.citylistapplication.service;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import vaspiakou.citylistapplication.dto.request.CityDto;
import vaspiakou.citylistapplication.exception.notfound.NotFoundException;
import vaspiakou.citylistapplication.model.City;
import vaspiakou.citylistapplication.repository.CityRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CityService.class})
@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @InjectMocks
    CityService cityService;
    @Mock
    CityRepository cityRepository;
    private final String TEST_STRING = "test";

    @Test
    void getCityByNameShouldReturnCityObject() {
        City city = new City();
        city.setId(123L);
        city.setName(TEST_STRING);
        city.setPhoto(TEST_STRING);
        Optional<City> optionalCity = Optional.of(city);
        when(cityRepository.findByName(any())).thenReturn(optionalCity);
        City actualCity = cityService.getCityByName(TEST_STRING);
        assertEquals(city, actualCity);
        verify(cityRepository).findByName(any());
    }

    @Test
    void getCityByNameWhenNotExistsInDBShouldThrowException() {
        when(cityRepository.findByName(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> cityService.getCityByName(TEST_STRING));
        verify(cityRepository).findByName(any());
    }

    @Test
    void getListOfCitiesShouldThrowException() {
        when(cityRepository.findAll((Pageable) any())).thenThrow(new NotFoundException(TEST_STRING));
        assertThrows(NotFoundException.class, () -> cityService.getPageOfCities(1));
        verify(cityRepository).findAll((Pageable) any());
    }

    @Test
    void whenEditCityByIdShouldEditCity() {
        City city = new City();
        city.setId(123L);
        city.setName(TEST_STRING);
        city.setPhoto(TEST_STRING);
        Optional<City> ofResult = Optional.of(city);

        City city1 = new City();
        city1.setId(123L);
        city1.setName(TEST_STRING);
        city1.setPhoto(TEST_STRING);
        when(cityRepository.save(any())).thenReturn(city1);
        when(cityRepository.findById(any())).thenReturn(ofResult);
        CityDto cityDto = mock(CityDto.class);
        when(cityDto.getId()).thenReturn(123L);
        when(cityDto.getName()).thenReturn(TEST_STRING);
        when(cityDto.getPhoto()).thenReturn(TEST_STRING);
        City actualEditCityByIdResult = cityService.editCityById(cityDto);
        assertSame(city, actualEditCityByIdResult);
        assertEquals(TEST_STRING, actualEditCityByIdResult.getPhoto());
        assertEquals(TEST_STRING, actualEditCityByIdResult.getName());
        verify(cityRepository).save(any());
        verify(cityRepository).findById((any()));
        verify(cityDto).getId();
        verify(cityDto).getName();
        verify(cityDto).getPhoto();
    }
}
