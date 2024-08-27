package com.example.passwordvg.controller;


import com.example.passwordvg.model.HashRequestDTO;
import com.example.passwordvg.services.ManageFiles;
import com.example.passwordvg.utils.HashAlgorithm;
import com.example.passwordvg.utils.HashUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageController {
    HashUtils hashUtils = new HashUtils();
    ManageFiles manageFiles = new ManageFiles();

    @GetMapping("/")
    public String redirect(){
        return "redirect:/login";
    }
    
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/crack")
    public String crack() {
        return "crackPassword";
    }

    @PostMapping("/hash")
    public String hash(Model model, @RequestParam String textToHash) {
        String hashedMD5 = hashUtils.hashText(textToHash, HashAlgorithm.MD5);
        String hashedSHA256 = hashUtils.hashText(textToHash, HashAlgorithm.SHA256);
        model.addAttribute("hashedMD5", hashedMD5);
        model.addAttribute("hashedSHA256", hashedSHA256);
        return "home";
    }

    @PostMapping("/password")
    public String password(Model model,@Valid @RequestParam HashRequestDTO hashToRetrieve){

        renderModel(hashToRetrieve,model);

        return "crackPassword";
    }

    private Model renderModel(HashRequestDTO hash, Model model){
        if (hash.get().contains("Not")){
            return model.addAttribute("retrievedPassword",hash.get());
        } else {
            String retrievedPassword = manageFiles.searchTextFile(hash.get());

            return model.addAttribute("retrievedPassword",retrievedPassword);
        }
    }
}
