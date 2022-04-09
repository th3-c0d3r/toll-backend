package io.github.th3c0d3r.tollbackend.controller;

import io.github.th3c0d3r.tollbackend.dto.RouteCostDto;
import io.github.th3c0d3r.tollbackend.entity.ApiResponse;
import io.github.th3c0d3r.tollbackend.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/map")
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping(path = "/route")
    public ApiResponse<RouteCostDto> getRoute(@RequestParam(name = "startLat") Double startLat, @RequestParam(name = "startLong") Double startLong,
                                              @RequestParam(name = "stopLat") Double stopLat, @RequestParam(name = "stopLong") Double stopLong){
        return new ApiResponse<>(mapService.getRoute(startLat, startLong, stopLat, stopLong));
    }

    @GetMapping(path = "/routeWithCost")
    public ApiResponse<RouteCostDto> getRouteWithCost(@RequestParam(name = "startLat") Double startLat, @RequestParam(name = "startLong") Double startLong,
                                                      @RequestParam(name = "stopLat") Double stopLat, @RequestParam(name = "stopLong") Double stopLong){
        return new ApiResponse<>(mapService.getCost(startLat, startLong, stopLat, stopLong));
    }
}
