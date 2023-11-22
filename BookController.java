package com.example.bookProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookProject.entity.Book;
import com.example.bookProject.repo.BookRepo;

@Controller
public class BookController {
	
	@Autowired
	BookRepo bookRepo;
	@PostMapping("/api/books")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
	      return new ResponseEntity<>(bookRepo.save(book),HttpStatus.CREATED);
		
	}
	/*it is used for theymleaf creatin this method*/
	@GetMapping("/")
	public String viewHomePage(Model model) {
		model.addAttribute("listBooks",bookRepo.getAllBooks());
		return "index";
	}
	
	@GetMapping("/showNewBookForm")
	public String showNewBookForm(Model model) {
		//create model attribute to bind form data
		Book book=new Book();
		model.addAttribute("book", book);
		return "newbook";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id,Model model ) {
		//get employee from the service
		Book book=bookRepo.getById(id);
		
		//set employee as a model attribute to pre-populate the form
		model.addAttribute("book", book);
		return "updatebook";
	}
	
	@GetMapping("/deleteBook/{id}")
	public String deleteEmployee(@PathVariable (value="id") long id) {
		
		//call delete employee method
		this.bookRepo.deleteById(id);
		return "redirect:/";
	}
	
	@PostMapping("/saveBook")
	public String saveBooks (@ModelAttribute("book") Book book){
		//save employee to database
		bookRepo.save(book);
		return "redirect:/";
	}
	
	@GetMapping("/api/books")
	public ResponseEntity<List<Book>> getBooks() {
		return new ResponseEntity<>(bookRepo.findAll(),HttpStatus.OK);
		
		
	}
	
	@GetMapping("/api/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable long id) {
      Optional<Book> book =bookRepo.findById(id);
		if(book.isPresent()) {
			return new ResponseEntity<>(book.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/api/books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable long id,@RequestBody Book boo) {
      Optional<Book> book =bookRepo.findById(id);
		if(book.isPresent()) {
			book.get().setBookName(boo.getBookName());
			book.get().setBookAuthor(boo.getBookAuthor());
			return new ResponseEntity<>(bookRepo.save(book.get()),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/api/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable long id) {
      Optional<Book> book =bookRepo.findById(id);
		if(book.isPresent()) {
			bookRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}
