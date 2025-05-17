package com.ashokit.controller;

import com.ashokit.dto.CounsellorsDto;
import com.ashokit.dto.DashboardDto;
import com.ashokit.service.CounsellorService;
import com.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.ashokit.constant.AppConstant.ERROR_MSG;
import static com.ashokit.constant.AppConstant.SUCESS_MSG;

@Controller
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

    @Autowired
    private EnquiryService enquiryService;

    public  CounsellorController(CounsellorService counsellorService, EnquiryService enquiryService) {

        this.counsellorService = counsellorService;
        this.enquiryService = enquiryService;
    }


    @GetMapping("/")
    public String index(Model model) {
        CounsellorsDto counsellorsDto = new CounsellorsDto();
        model.addAttribute("counsellor", counsellorsDto);
        return "index";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("counsellor") CounsellorsDto dto, HttpServletRequest request, Model model) {

        CounsellorsDto counsellorsDto = counsellorService.login(dto.getEmail(), dto.getPwd());
        if (counsellorsDto != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("COUNSELLOR_ID", counsellorsDto.getCounsellorId());
            return "redirect:dashboard";
        }
        else {

            model.addAttribute(ERROR_MSG,"Invalid Credentails");
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String buildDashboard(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        Integer counsellorId = (Integer)session.getAttribute("COUNSELLOR_ID");

        DashboardDto dashboardInfo = enquiryService.getDashboardInfo(counsellorId);
        model.addAttribute("dashboardInfo", dashboardInfo);
        return "dashboardReportView";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        CounsellorsDto counsellorsDto = new CounsellorsDto();
        model.addAttribute("counsellor", counsellorsDto);
        return "registerView";
    }

  @PostMapping("/register")
  public String handleRegistration(@ModelAttribute("counsellor") CounsellorsDto dto, Model model) {

      boolean emailUnique = counsellorService.isEmailUnique(dto.getEmail());

      if (emailUnique) {
          boolean register = counsellorService.register(dto);
          if (register) {
              model.addAttribute(SUCESS_MSG,"Resgister sucessfully!!");
          }else {
              model.addAttribute(ERROR_MSG,"Failed registration");
          }
      }else {
          model.addAttribute(ERROR_MSG,"Duplicate email");
      }

      return "registerView";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
}
