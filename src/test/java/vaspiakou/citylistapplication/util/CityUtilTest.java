package vaspiakou.citylistapplication.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vaspiakou.citylistapplication.exception.notfound.CityNotFoundException;
import vaspiakou.citylistapplication.repository.CityRepository;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityUtilTest {

    @InjectMocks
    CityUtil cityUtil;
    @Mock
    CityRepository cityRepository;

    private final String TEST = "test";

    @Test
    void whenFindCityByInValidNameShouldThrowException(){
        Assertions.assertThrows(CityNotFoundException.class, () -> cityUtil.findCityByName(TEST));
    }

    @Test
    void whenFindCityByInValidIdShouldThrowException(){
        Assertions.assertThrows(CityNotFoundException.class, () -> cityUtil.findCityById(1L));
    }

    @Test
    void whenGetCitiesShouldThrowException() {
        when(cityRepository.findAll((Pageable) any())).thenThrow(new CityNotFoundException("Name"));
        assertThrows(CityNotFoundException.class, () -> cityUtil.getCities(1));
        verify(cityRepository).findAll((Pageable) any());
    }

    @Test
    void whenGetCitiesThenReturnEmptyPage() {
        when(cityRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(cityUtil.getCities(1).isEmpty());
        verify(cityRepository).findAll((Pageable) any());
    }
}
