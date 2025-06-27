package com.corhuila.AgendaManager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corhuila.AgendaManager.Entity.Country;
import com.corhuila.AgendaManager.IRepository.IBaseRepository;
import com.corhuila.AgendaManager.IRepository.ICountryRepository;
import com.corhuila.AgendaManager.IService.ICountryService;
@Service
public class CountryService extends ABaseService<Country> implements ICountryService {
    @Override
    protected IBaseRepository<Country, Long> getRepository() {
        return repository;
    }
    @Autowired
    private ICountryRepository repository;
}
