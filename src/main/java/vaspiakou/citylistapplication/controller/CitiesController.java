package vaspiakou.citylistapplication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vaspiakou.citylistapplication.dto.request.CityDto;
import vaspiakou.citylistapplication.dto.response.SuccessResponse;
import vaspiakou.citylistapplication.model.City;
import vaspiakou.citylistapplication.service.CityService;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/city")
@Slf4j
public class CitiesController {

    private final CityService cityService;

    @GetMapping("/{name}")
    public City getCityByName(@PathVariable(value = "name") String cityName) {
        log.info("REQUEST GET /city/" + cityName);
        return cityService.findCityByName(cityName);
    }

    @GetMapping("/list/{page}")
    public List<City> getPageOfCities(@PathVariable(value = "page") int page) {
        log.info("REQUEST GET /city/list/" + page);
        return cityService.getPageOfCities(page);
    }

    @PatchMapping("/editing")
    public SuccessResponse editCity(@RequestBody CityDto cityDto) {
        log.info("REQUEST PATCH /city/editing");
        City city = cityService.editCity(cityDto);
        return new SuccessResponse(String.format("The info about %s is edited successfully.", city.getName()));
    }

    @PostMapping("/load")
    public SuccessResponse uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("REQUEST POST /city/load");
        cityService.resolveFile(file);
        return new SuccessResponse("File was saved successfully");
    }
}
