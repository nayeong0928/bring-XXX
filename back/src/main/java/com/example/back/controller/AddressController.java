package com.example.back.controller;

import com.example.back.dto.AddressDto;
import com.example.back.entity.Address;
import com.example.back.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/address/new")
    public String addr() throws IOException {
        addressService.parseExcel();
        return "redirect:/";
    }

    @GetMapping("/address")
    public String addrList(Model model){
        model.addAttribute("addresses", addressService.getAddressAll());
        return "/address/addressList";
    }

    @GetMapping("/address/all")
    @ResponseBody
    public List<AddressDto> searchAddrList(){
        return addressService.getAddressAll().stream()
                .map(address -> new AddressDto(address.getId(), address.toString()))
                .collect(Collectors.toList());
    }
}
