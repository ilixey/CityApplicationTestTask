package vaspiakou.citylistapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vaspiakou.citylistapplication.dto.request.CityDto;
import vaspiakou.citylistapplication.exception.FileErrorException;
import vaspiakou.citylistapplication.model.City;
import vaspiakou.citylistapplication.util.CityUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityService {

    private final CityUtil cityUtil;

    public City findCityByName(String name){
        return cityUtil.findCityByName(name);
    }

    public List<City> getPageOfCities(int page){
        return cityUtil.getCities(page);
    }

    public City editCity(CityDto cityDto) {
        City city = cityUtil.findCityById(cityDto.getId());
        cityUtil.editCity(city, cityDto);
        return city;
    }

    public void resolveFile(MultipartFile file) throws IOException {
        if (!cityUtil.hasCSVFormat(file)) throw new FileErrorException("Failed to parse csv file");
        List<City> cities = csvToCities(file.getInputStream());
        cityUtil.saveCities(cities);
    }

    private List<City> csvToCities(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<City> cities = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                City city = City.builder()
                                .id(Long.parseLong(csvRecord.get("id")))
                                .name(csvRecord.get("name"))
                                .photo(csvRecord.get("photo"))
                                .build();
                cities.add(city);
            }
            return cities;
        } catch (IOException e) {
            throw new FileErrorException("Failed to parse CSV file");
        }
    }
}
