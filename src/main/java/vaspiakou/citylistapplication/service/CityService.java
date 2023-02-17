package vaspiakou.citylistapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vaspiakou.citylistapplication.dto.request.CityDto;
import vaspiakou.citylistapplication.exception.notfound.NotFoundException;
import vaspiakou.citylistapplication.model.City;
import vaspiakou.citylistapplication.repository.CityRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityService {

    private final CityRepository cityRepository;
    public City getCityByName(String name){
        return cityRepository.findByName(name).orElseThrow(() -> {
            throw new NotFoundException(String.format("The city with name %s is not found", name));
        });
    }

    public Page<City> getPageOfCities(int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id"));
        Page<City> cityPage = cityRepository.findAll(pageable);
        return cityPage;
    }

    public City editCityById(CityDto cityDto) {
        Long id = cityDto.getId();
        City city = cityRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format("The city with id %d not found", id));
        });
        city.setName(cityDto.getName());
        city.setPhoto(cityDto.getPhoto());
        cityRepository.save(city);
        return city;
    }
}
