package io.github.th3c0d3r.tollbackend.controller;

import io.github.th3c0d3r.tollbackend.dto.TollPlazaDto;
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
    public TollPlazaDto getByTollPlazaId(@PathVariable(name = "tollPlazaId") Integer tollPlazaId) throws Exception {
        return tollPlazaService.getByTollPlazaId(tollPlazaId);
    }

    @GetMapping
    @RequestMapping(path = "/tollPlaza/test")
    public void test() throws InterruptedException {
        tollPlazaService.reverseGeoCode();
    }

    @GetMapping
    @RequestMapping(path = "/tollPlaza/getAll")
    public List<TollPlazaDto> getByStateAndTollName(@Nullable @RequestParam(name = "stateName") String stateName, @Nullable @RequestParam(name = "tollName") String tollName) {
        return tollPlazaService.getByStateAndTollName(stateName, tollName);
    }

    @GetMapping
    @RequestMapping(path = "/getAllTollNames")
    public List<String> getAllTollPlazaNamesByState(@Nullable @RequestParam(name = "stateName") String stateName) {
        return tollPlazaService.getAllTollPlazaNamesByState(stateName);
    }

    @GetMapping
    @RequestMapping(path = "/getAllStateNames")
    public List<String> getAllTollPlazaStateNames() {
        return tollPlazaService.getAllTollPlazaStateNames();
    }

// TODO: complete cron and remove endpoint.
// TODO: Exception handling.
}
