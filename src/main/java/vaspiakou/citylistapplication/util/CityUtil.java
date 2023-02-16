package vaspiakou.citylistapplication.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import vaspiakou.citylistapplication.dto.request.CityDto;
import vaspiakou.citylistapplication.exception.notfound.CityNotFoundException;
import vaspiakou.citylistapplication.model.City;
import vaspiakou.citylistapplication.repository.CityRepository;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CityUtil {

    private final CityRepository cityRepository;

    public City findCityByName(String name){
        return cityRepository.findByName(name).orElseThrow(() -> {
            throw new CityNotFoundException(name);
        });
    }

    public List<City> getCities(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<City> cityPage = cityRepository.findAll(pageable);
        return cityPage.getContent();
    }

    public City findCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> {
            throw new CityNotFoundException(id);
        });
    }

    public void editCity(City city, CityDto cityDto) {
        city.setName(cityDto.getName());
        city.setPhoto(cityDto.getPhoto());
        cityRepository.save(city);
    }

    public boolean hasCSVFormat(MultipartFile file) {
        return file.getContentType().equals("csv");
    }

    public void saveCities(List<City> cities) {
        cityRepository.saveAll(cities);
    }
}
