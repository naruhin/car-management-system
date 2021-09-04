package com.naruhin.springbootexamplehillelhw5.web;


import com.naruhin.springbootexamplehillelhw5.domain.Address;
import com.naruhin.springbootexamplehillelhw5.domain.Dealer;
import com.naruhin.springbootexamplehillelhw5.repository.AddressRepository;
import com.naruhin.springbootexamplehillelhw5.repository.DealerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class DealerRestController {
    private final AddressRepository addressRepository;
    private final DealerRepository dealerRepository;

    public DealerRestController(AddressRepository addressRepository, DealerRepository dealerRepository) {
        this.addressRepository = addressRepository;
        this.dealerRepository = dealerRepository;
    }

    //Операция сохранения дилера в базу данных
    @PostMapping("/dealers/{addressId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Dealer saveAddress(@RequestBody Dealer dealer, @PathVariable long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id = " + addressId));
        dealer.setAddress(address);
        return dealerRepository.save(dealer);
    }

    //Получение списка дилеров
    @GetMapping("/dealers")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Dealer> getAllDealers() {
        return dealerRepository.findAll();
    }

    //Удаление всех дилеров
    @DeleteMapping("/dealers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllDealers() {
        dealerRepository.deleteAll();
    }

    //Обновление дилера
    @PutMapping("/dealers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Dealer updateAddress(@PathVariable("id") long id, @RequestBody Dealer dealer) {
        return dealerRepository.findById(id)
                .map(entity -> {
                    entity.setName(dealer.getName());
                    return dealerRepository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Dealer with id = Not found"));
    }
}
