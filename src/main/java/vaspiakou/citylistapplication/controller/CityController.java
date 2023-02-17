package vaspiakou.citylistapplication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vaspiakou.citylistapplication.dto.request.CityDto;
import vaspiakou.citylistapplication.dto.response.SuccessResponse;
import vaspiakou.citylistapplication.model.City;
import vaspiakou.citylistapplication.service.CityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@Slf4j
public class CityController {

    private final CityService cityService;

    @GetMapping("/city/{name}")
    public City getCityByName(@PathVariable(value = "name") String cityName) {
        log.info("REQUEST GET /city/" + cityName);
        return cityService.getCityByName(cityName);
    }

    @GetMapping("/getCities/{page}")
    public Page<City> getPageOfCities(@PathVariable(value = "page") int page) {
        log.info("REQUEST GET /city/list/" + page);
        return cityService.getPageOfCities(page);
    }

    @PatchMapping("/city/editing")
    public SuccessResponse editCity(@RequestBody CityDto cityDto) {
        log.info("REQUEST PATCH /city/editing");
        City city = cityService.editCityById(cityDto);
        return new SuccessResponse(String.format("The info about city with id=%d is edited successfully.", city.getId()));
    }
}
