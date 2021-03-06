package core.controller.impl;


import core.config.mapper.AddressMapper;
import core.domain.Address;
import core.domain.Dealer;
import core.service.AddressService;
import core.service.DealerService;
import core.config.mapper.DealerMapper;
import com.naruhin.api.dto.DealerDTO;
import com.naruhin.api.controller.DealerRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class DealerRestControllerImpl implements DealerRestController {
    private final AddressService addressService;
    private final DealerService dealerService;

    public DealerRestControllerImpl(AddressService addressService, DealerService dealerService) {
        this.addressService = addressService;
        this.dealerService = dealerService;
    }


    @PostMapping("/dealers/{addressId}")
    @ResponseStatus(HttpStatus.CREATED)
    public DealerDTO saveAddress(@RequestBody DealerDTO dealerDTO, @PathVariable long addressId) {
        Address address = addressService.getAddressByID(addressId);
        Dealer dealer = DealerMapper.INSTANCE.toDealer(dealerDTO);
        dealerDTO.setAddress(AddressMapper.INSTANCE.toAddressDto(address));
        return DealerMapper.INSTANCE.toDealerDto(dealerService.saveDealer(dealer,addressId));
    }


    @GetMapping("/dealers")
    @ResponseStatus(HttpStatus.OK)
    public Collection<DealerDTO> getAllDealers() {
        Collection<Dealer> dealers = dealerService.getAllDealers();
        return DealerMapper.INSTANCE.map((List<Dealer>) dealers);
    }


    @DeleteMapping("/dealers")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllDealers() {
        dealerService.removeAllDealers();
    }


    @PutMapping("/dealers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DealerDTO updateDealer(@PathVariable("id") long id, @RequestBody DealerDTO dealerDTO) {
        Dealer dealer = DealerMapper.INSTANCE.toDealer(dealerDTO);
        return  DealerMapper.INSTANCE.toDealerDto(dealerService.updateDealer(id, dealer));
    }
}
