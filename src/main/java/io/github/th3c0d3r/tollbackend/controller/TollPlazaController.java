package io.github.th3c0d3r.tollbackend.controller;

import io.github.th3c0d3r.tollbackend.dto.TollPlazaDto;
import io.github.th3c0d3r.tollbackend.service.TollPlazaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tollPlaza")
public class TollPlazaController {

    @Autowired
    private TollPlazaService tollPlazaService;

    @PostMapping
    @RequestMapping(path = "/cron")
    public void runCron(){
        tollPlazaService.populateTable();
    }

    @GetMapping
    @RequestMapping(path = "/{tollPlazaId}")
    public TollPlazaDto getByTollPlazaId(@PathVariable(name = "tollPlazaId") Integer tollPlazaId) throws Exception {
        return tollPlazaService.getByTollPlazaId(tollPlazaId);
    }
// TODO: complete cron and remove endpoint.
// TODO: response.
// TODO: Exception handling.
// TODO: bucketing.
}
