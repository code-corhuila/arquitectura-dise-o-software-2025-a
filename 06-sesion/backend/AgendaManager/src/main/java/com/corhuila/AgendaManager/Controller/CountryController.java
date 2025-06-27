package com.corhuila.AgendaManager.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corhuila.AgendaManager.Entity.Country;
import com.corhuila.AgendaManager.IService.ICountryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/country")
public class CountryController extends ABaseController<Country, ICountryService> {
    public CountryController(ICountryService service) {
        super(service, "Country");
    }
}
