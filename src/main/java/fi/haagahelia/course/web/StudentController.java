package fi.haagahelia.course.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.course.domain.Motivo;
import fi.haagahelia.course.domain.MotivoRepository;
import fi.haagahelia.course.domain.Citas;
import fi.haagahelia.course.domain.StudentRepository;

@Controller
public class StudentController {
	@Autowired
	private StudentRepository repository;

	@Autowired
	private MotivoRepository mrepository;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/students")
	public String index(Model model) {
		List<Citas> students = (List<Citas>) repository.findAll();
		model.addAttribute("students", students);
		return "students";
	}

	@RequestMapping(value = "add")
	public String addStudent(Model model) {
		model.addAttribute("student", new Citas());
		return "addStudent";
	}

	@RequestMapping(value = "/edit/{id}")
	public String editStudent(@PathVariable("id") Long studentId, Model model) {
		Optional<Citas> studentOptional = repository.findById(studentId);
		Citas student = studentOptional.orElse(new Citas());
		model.addAttribute("student", student);
		return "editStudent";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	  public String save(@RequestParam(value = "action", required = true) String action, Citas student) {
        if (action.equalsIgnoreCase("save")) {
            repository.save(student);
        }
        return "redirect:/students";
    }

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteStudent(@PathVariable("id") Long studentId, Model model) {
		repository.deleteById(studentId);
		return "redirect:/students";
	}

	@RequestMapping(value = "addStudentmotivo/{id}", method = RequestMethod.GET)
	public String addMotivo(@PathVariable("id") Long studentId, Model model) {

		model.addAttribute("motivo", mrepository.findAll());
		model.addAttribute("student", repository.findById(studentId).get());
		return "addStudentMotivo";
	}

	@RequestMapping(value = "/student/{id}/motivo", method = RequestMethod.GET)
	public String studentsAddMotivo(@RequestParam(value = "action", required = true) String action,
			@PathVariable Long id, @RequestParam Long motivoId, Model model) {
		Optional<Motivo> motivo = mrepository.findById(motivoId);
		Optional<Citas> student = repository.findById(id);

		if (student.isPresent() && action.equalsIgnoreCase("save")) {
			if (!student.get().hasMotivo(motivo.get())) {
				student.get().getMotivo().add(motivo.get());
			}
			repository.save(student.get());
			model.addAttribute("student", mrepository.findById(id));
			model.addAttribute("courses", mrepository.findAll());
			return "redirect:/students";
		}

		model.addAttribute("developers", repository.findAll());
		return "redirect:/students";

	}

	@RequestMapping(value = "getstudents", method = RequestMethod.GET)
	public @ResponseBody List<Citas> getStudents() {
		return (List<Citas>) repository.findAll();
	}
}
