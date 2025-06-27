package com.corhuila.AgendaManager.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corhuila.AgendaManager.Entity.Continent;
import com.corhuila.AgendaManager.IService.IContinentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/continent")
public class ContinentController extends ABaseController<Continent, IContinentService> {
    public ContinentController(IContinentService service) {
        super(service, "Continent");
    }
}
