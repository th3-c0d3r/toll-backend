package io.github.th3c0d3r.tollbackend.controller;

import io.github.th3c0d3r.tollbackend.dto.TollPlazaDto;
import io.github.th3c0d3r.tollbackend.entity.ApiResponse;
import io.github.th3c0d3r.tollbackend.service.TollPlazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/")
public class TollPlazaController {

    @Autowired
    private TollPlazaService tollPlazaService;

    @PostMapping
    @RequestMapping(path = "/tollPlaza/cron")
    public void runCron() throws InterruptedException {
        tollPlazaService.populateTable();
        tollPlazaService.reverseGeoCode();
    }

    @GetMapping
    @RequestMapping(path = "/tollPlaza/{tollPlazaId}")
    public ApiResponse<TollPlazaDto> getByTollPlazaId(@PathVariable(name = "tollPlazaId") Integer tollPlazaId) throws Exception {
        return new ApiResponse<>(tollPlazaService.getByTollPlazaId(tollPlazaId));
    }

    @GetMapping
    @RequestMapping(path = "/tollPlaza/getAll")
    public ApiResponse<List<TollPlazaDto>> getByStateAndTollName(@Nullable @RequestParam(name = "stateName") String stateName, @Nullable @RequestParam(name = "districtName") String districtName) {
        return new ApiResponse<>(tollPlazaService.getByStateAndTollName(stateName, districtName));
    }

    @GetMapping
    @RequestMapping(path = "/getAllTollNames")
    public ApiResponse<List<String>> getAllTollPlazaNamesByState(@Nullable @RequestParam(name = "stateName") String stateName) {
        return new ApiResponse<>(tollPlazaService.getAllTollPlazaNamesByState(stateName));
    }

    @GetMapping
    @RequestMapping(path = "/getAllStateNames")
    public ApiResponse<List<String>> getAllTollPlazaStateNames() {
        return new ApiResponse<>(tollPlazaService.getAllTollPlazaStateNames());
    }

    @GetMapping
    @RequestMapping(path = "/getAllDistrict")
    public ApiResponse<List<String>> getAllDistrictsByState(@Nullable @RequestParam(name = "stateName") String stateName) {
        return new ApiResponse<>(tollPlazaService.getAllDistrictsByState(stateName));
    }

// TODO: complete cron and remove endpoint.
// TODO: Exception handling.
}
