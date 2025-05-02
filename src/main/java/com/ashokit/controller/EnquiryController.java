package com.ashokit.controller;

import com.ashokit.dto.DashboardDto;
import com.ashokit.dto.EnquiryDto;
import com.ashokit.entity.Enquiry;
import com.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        model.addAttribute("enquiries",enquiryDto);
        return "enquiry";
    }

    @PostMapping("/save")
    public String buildEnquiry(@ModelAttribute ("enquiries") EnquiryDto enquiryDto, HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer)session.getAttribute("COUNSELLOR_ID");
        boolean savedEnq = enquiryService.upsertEnquiry(enquiryDto, counsellorId);
        if (savedEnq) {
            model.addAttribute("smsg", "Sucessfully saved Enquiry");
           // return "enquiry";
        } else {
            model.addAttribute("emsg", "Failed enquiry");
        }
        return "enquiry";

    }

    @GetMapping("/view")
    public String buildViewAllEnquiry(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("COUNSELLOR_ID");
        List<EnquiryDto> enquiries = enquiryService.getEnquiries(counsellorId);
        model.addAttribute("enquiries", enquiries);

        EnquiryDto searchFormdata = new EnquiryDto();
        model.addAttribute("filterDto", searchFormdata);
        return "viewEnquiry";
    }


    @PostMapping("/filter-enqs")
    public String filterEnquiry(@ModelAttribute("filterDto") EnquiryDto filterDto , HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer) session.getAttribute("COUNSELLOR_ID");
        List<EnquiryDto> enquiries = enquiryService.filterEnqs(filterDto, counsellorId);
        model.addAttribute("enquiries", enquiries);
        return "viewEnquiry";
    }

    @GetMapping("/edit-enquiry")
    public String editEnquiry(@RequestParam("enqId") Integer enqId, EnquiryDto enquiryDto ,Model model) {
        EnquiryDto enquiry = enquiryService.getEnquiry(enqId, enquiryDto);
        model.addAttribute("enquiries",enquiry);
        return "enquiry"; // bind to the form
    }

    @GetMapping("/delete-enquiry")
    public String deleteEnquiry(@RequestParam("enqId") Integer enqId,Model model) {
        enquiryService.deleteEnquiry(enqId);
        return "redirect:view"; // bind to the form
    }


























   /* @PostMapping("/addEnquiry")
    public String addEnquiry(@RequestBody EnquiryDto enquiryDto, @RequestParam Integer counsellorId) {
        return enquiryService.upsertEnquiry(enquiryDto,counsellorId);
    }

    @GetMapping("/dashboard/{counsellorId}")
    public DashboardDto getDashboardInfo(@PathVariable Integer counsellorId) {
        return enquiryService.getDashboardInfo(counsellorId);
    }

    @GetMapping("/allEnquires/{counsellorId}")
    public List<EnquiryDto> getEnquiriesLogedUSer(@PathVariable Integer counsellorId) {
       return enquiryService.getEnquiries(counsellorId);
    }

    @PutMapping("/edit")
    public EnquiryDto editEnquiry(@PathVariable Integer enqId) {
        return enquiryService.getEnquiry(enqId);
    }

    @GetMapping("/filter")
    public List<EnquiryDto> filterEnqs(@RequestBody EnquiryDto filterDto, @PathVariable Integer counsellorId) {
       return enquiryService.filterEnqs(filterDto, counsellorId);
    }*/
}
