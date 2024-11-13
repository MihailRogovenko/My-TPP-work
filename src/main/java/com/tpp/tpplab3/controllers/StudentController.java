package com.tpp.tpplab3.controllers;

import com.tpp.tpplab3.models.Students;
import com.tpp.tpplab3.service.StudentService;
import com.tpp.tpplab3.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private GroupService groupService;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Students());
        model.addAttribute("groups", groupService.getAllGroups());
        return "add-student";
    }

    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute("student") Students student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("groups", groupService.getAllGroups());
            return "add-student";
        }
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable("id") Integer id, Model model) {
        Optional<Students> studentOpt = studentService.findStudentById(id);
        if (studentOpt.isPresent()) {
            model.addAttribute("student", studentOpt.get());
            model.addAttribute("groups", groupService.getAllGroups());
            return "edit-student";
        }
        return "redirect:/students";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") Integer id, @Valid @ModelAttribute("student") Students student,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("groups", groupService.getAllGroups());
            return "edit-student";
        }
        student.setStudentId(id);
        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id) {
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }
}
