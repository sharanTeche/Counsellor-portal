package com.ashokit.controller;


import com.ashokit.dto.EnquiryDto;
import com.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.ashokit.constant.AppConstant.CONSELLOR_IDS;
import static com.ashokit.constant.AppConstant.ENQUIRYES_DEATAILS;
import static com.ashokit.constant.AppConstant.ENQUIRY_PAGE;
import static com.ashokit.constant.AppConstant.ERROR_MSG;
import static com.ashokit.constant.AppConstant.SUCESS_MSG;

@Controller
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    public EnquiryController(EnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @GetMapping("/addEnquiry")
    public String addEnquiry(Model model) {
        EnquiryDto enquiryDto = new EnquiryDto();
        model.addAttribute(ENQUIRYES_DEATAILS,enquiryDto);
        return ENQUIRY_PAGE;
    }

    @PostMapping("/save")
    public String buildEnquiry(@ModelAttribute ("enquiries") EnquiryDto enquiryDto, HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer)session.getAttribute(CONSELLOR_IDS);
        boolean savedEnq = enquiryService.upsertEnquiry(enquiryDto, counsellorId);
        if (savedEnq) {
            model.addAttribute(SUCESS_MSG, "Sucessfully saved Enquiry");
        } else {
            model.addAttribute(ERROR_MSG, "Failed enquiry");
        }
        return ENQUIRY_PAGE;

    }

    @GetMapping("/view")
    public String buildViewAllEnquiry(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute(CONSELLOR_IDS);
        List<EnquiryDto> enquiries = enquiryService.getEnquiries(counsellorId);
        model.addAttribute(ENQUIRYES_DEATAILS, enquiries);

        EnquiryDto searchFormdata = new EnquiryDto();
        model.addAttribute("filterDto", searchFormdata);
        return "viewEnquiry";
    }


    @PostMapping("/filter-enqs")
    public String filterEnquiry(@ModelAttribute("filterDto") EnquiryDto filterDto , HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute(CONSELLOR_IDS);
        List<EnquiryDto> enquiries = enquiryService.filterEnqs(filterDto, counsellorId);
        model.addAttribute(ENQUIRYES_DEATAILS, enquiries);
        return "viewEnquiry";
    }

    @GetMapping("/edit-enquiry")
    public String editEnquiry(@RequestParam("enqId") Integer enqId, EnquiryDto enquiryDto ,Model model) {
        EnquiryDto enquiry = enquiryService.getEnquiry(enqId, enquiryDto);
        model.addAttribute(ENQUIRYES_DEATAILS,enquiry);
        return ENQUIRY_PAGE;
    }

    @GetMapping("/delete-enquiry")
    public String deleteEnquiry(@RequestParam("enqId") Integer enqId,Model model) {
        enquiryService.deleteEnquiry(enqId);
        return "redirect:view";
    }
}
