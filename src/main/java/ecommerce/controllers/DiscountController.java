package ecommerce.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.models.DiscountModel;
import ecommerce.services.DiscountService;

@RestController
public class DiscountController {
    @Autowired private DiscountService discountService;

    @PostMapping("addDiscount")
    public String addDiscount(Principal principal,@RequestBody DiscountModel discountModel) {
        return discountService.addDiscount(principal.getName(),discountModel);
    }

    @GetMapping("viewDiscounts")
    public List<DiscountModel> viewDiscounts(Principal principal){
        return discountService.viewDiscounts(principal.getName());
    }

    @GetMapping("discountValidation")
    public List<DiscountModel> discountValidation(@RequestParam("orderId") long orderId,@RequestParam("enteredCoupon") String enteredCoupon) {
        System.out.println(orderId);
        return discountService.discountValidation(enteredCoupon,orderId);
    }


}
