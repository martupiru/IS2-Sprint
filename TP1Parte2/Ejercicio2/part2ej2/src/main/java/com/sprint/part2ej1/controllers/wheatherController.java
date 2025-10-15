package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.models.WeatherResponse;
import com.sprint.part2ej1.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/weather")
public class wheatherController {

    @Autowired
    private final WeatherService weatherService;

    public wheatherController(final WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public Mono<WeatherResponse> getWeather(@RequestParam String city){
        return weatherService.getWeatherByCity(city);
    }

}
