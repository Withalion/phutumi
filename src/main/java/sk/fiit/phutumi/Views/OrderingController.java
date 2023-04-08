package sk.fiit.phutumi.Views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderingController {

    @GetMapping("/phutumi")
    public String mainPage(Model model){
        model.addAttribute("name", "Phutumi");
        return "mainPage";
    }
}
