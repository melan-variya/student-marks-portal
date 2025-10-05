package com.example.studentportal.controller;

import com.example.studentportal.model.Student;
import com.example.studentportal.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/students";
    }

    // ====== Views for TEACHER ======
    @GetMapping("/students")
    public String list(Model model, @ModelAttribute("message") String message) {
        model.addAttribute("students", service.findAll());
        return "students/list";
    }

    @GetMapping("/students/new")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("mode", "create");
        return "students/form";
    }

    @PostMapping("/students")
    public String create(@Valid @ModelAttribute("student") Student student,
                         BindingResult bindingResult,
                         RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return "students/form";
        }
        service.save(student);
        ra.addFlashAttribute("success", "Student created successfully.");
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Optional<Student> opt = service.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("error", "Student not found.");
            return "redirect:/students";
        }
        model.addAttribute("student", opt.get());
        model.addAttribute("mode", "edit");
        return "students/form";
    }

    @PostMapping("/students/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("student") Student student,
                         BindingResult bindingResult,
                         RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            return "students/form";
        }
        student.setId(id);
        service.save(student);
        ra.addFlashAttribute("success", "Student updated successfully.");
        return "redirect:/students";
    }

    @PostMapping("/students/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.deleteById(id);
        ra.addFlashAttribute("success", "Student deleted.");
        return "redirect:/students";
    }

    // ====== View for STUDENT ======
    @GetMapping("/me")
    public String myMarks(Authentication auth, Model model) {
        String username = auth.getName();
        Optional<Student> opt = service.findByContact(username);
        if (opt.isEmpty()) {
            model.addAttribute("error", "No student profile linked to your account.");
            return "students/view";
        }
        model.addAttribute("student", opt.get());
        return "students/view";
    }

    // ====== Simple REST API for TEACHER ======
    @GetMapping("/api/students")
    @ResponseBody
    public List<Student> apiList() {
        return service.findAll();
    }

    @GetMapping("/api/students/{id}")
    @ResponseBody
    public ResponseEntity<Student> apiGet(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/students")
    @ResponseBody
    public ResponseEntity<Student> apiCreate(@Valid @RequestBody Student s) {
        Student saved = service.save(s);
        return ResponseEntity.created(URI.create("/api/students/" + saved.getId())).body(saved);
    }

    @PutMapping("/api/students/{id}")
    @ResponseBody
    public ResponseEntity<Student> apiUpdate(@PathVariable Long id, @Valid @RequestBody Student s) {
        if (service.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        s.setId(id);
        return ResponseEntity.ok(service.save(s));
    }

    @DeleteMapping("/api/students/{id}")
    @ResponseBody
    public ResponseEntity<Void> apiDelete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ====== Auth ======
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
