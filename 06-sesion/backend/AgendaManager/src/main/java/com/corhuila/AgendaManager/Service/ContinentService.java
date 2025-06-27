package com.corhuila.AgendaManager.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corhuila.AgendaManager.Entity.Continent;
import com.corhuila.AgendaManager.IRepository.IBaseRepository;
import com.corhuila.AgendaManager.IRepository.IContinentRepository;
import com.corhuila.AgendaManager.IService.IContinentService;

@Service
public class ContinentService extends ABaseService<Continent> implements IContinentService {
    @Override
    protected IBaseRepository<Continent, Long> getRepository() {
        return repository;
    }
    
    @Autowired
    private IContinentRepository repository;
}
