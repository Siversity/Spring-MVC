package galerie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import galerie.dao.TableauRepository;
import galerie.entity.Tableau;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/tableau")
public class TableauController {
    
    @Autowired
    private TableauRepository dao;
    
    @GetMapping(path = "show")
    public String afficheTousLesTableaux(Model model) {
        model.addAttribute("tableaux", dao.findAll());
        return "afficheTableaux";
    }
    
    @GetMapping(path = "add")
    public String montreLeFormulairePourAjout(@ModelAttribute("tableau") Tableau tableau) {
        return "formulaireTableau";
    }
    
    @PostMapping(path = "save")
    public String ajouteLeTableauPuisMontreLaListe(Tableau tableau, RedirectAttributes redirectInfo) {
        String message;
        try {
            dao.save(tableau);
            message = "Le tableau '" + tableau.getTitre() + "' a été correctement enregistré";
        } catch (Exception e) {
            message = "Erreur : Le tableau '" + tableau.getTitre() + "' n'a pas pu être enregistré";
        }
        redirectInfo.addFlashAttribute("message", message);
        return "redirect:show";
    }

}
