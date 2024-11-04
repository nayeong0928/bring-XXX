package com.example.back.controller;

import com.example.back.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

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
}
