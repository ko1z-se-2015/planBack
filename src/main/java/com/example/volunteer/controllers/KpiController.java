package com.example.volunteer.controllers;

import com.example.volunteer.entities.KPI;
import com.example.volunteer.modules.KPIsToDelete;
import com.example.volunteer.services.KpiService;
import com.example.volunteer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/kpi")
public class KpiController {

    private final KpiService kpiService;
    private final UserService userService;

    public KpiController(KpiService kpiService, UserService userService ) {
        this.kpiService = kpiService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity saveMyAcademicWork(@RequestHeader(value = "Authorization") String token,
                                             @RequestParam Long kpiSectionId,
                                             @RequestBody KPI kpi) {
        kpiService.saveKpi(kpiSectionId, kpi);
        return new ResponseEntity("kpi added", HttpStatus.CREATED);
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity deleteAcademicWorkById(@RequestParam Long id) {
        kpiService.deleteById(id);
        return new ResponseEntity("kpi is deleted", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteAcademicWorks(@RequestBody KPIsToDelete kpis) {
        for(KPI item: kpis.getItems()){
            kpiService.deleteById(item.getId());
        }
        return new ResponseEntity("kpis are deleted", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity  updateAcademicWorkById(@RequestBody KPI kpi){
        kpiService.update(kpi);
        return new ResponseEntity("kpi is updated", HttpStatus.OK);
    }

}
