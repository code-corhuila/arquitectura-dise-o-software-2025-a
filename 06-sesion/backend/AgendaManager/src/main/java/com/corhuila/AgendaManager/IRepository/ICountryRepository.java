package com.corhuila.AgendaManager.IRepository;

import com.corhuila.AgendaManager.Entity.Country;

import org.springframework.stereotype.Repository;

@Repository
public interface ICountryRepository extends IBaseRepository<Country,Long>{
}
