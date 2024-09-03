package com.jbk.Controller;



	import java.util.List;

	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.hibernate.Transaction;
	import org.hibernate.query.Query;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.web.bind.annotation.CrossOrigin;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	import com.tka.entity.Question;

	@RestController
	@CrossOrigin("http://localhost:4200")
	public class QuestionManagement 
	{
		@Autowired
		SessionFactory factory;

	// @PostMapping indicate that client must use HTTP request method post while requesting this  API (method) .

		@PostMapping("addQuestion")
		public boolean addQuestion(@RequestBody Question question)
		{
			Session session = factory.openSession();
			
			Transaction tx = session.beginTransaction();
			
				session.persist(question);
				
			tx.commit();
			
			return true;
				
		}
		
		
		@PutMapping("updateQuestion")
		public boolean updateQuestion(@RequestBody Question question)
		{
			Session session = factory.openSession();
			
			Transaction tx = session.beginTransaction();
			
				session.merge(question);
				
			tx.commit();
			
			return true;
				
		}
		// @PathVariable indicate spring to copy path variable's value to method parameter

		@GetMapping("viewQuestion/{qno}/{subject}")
		public Question viewQuestion(@PathVariable Integer qno, @PathVariable String subject)
		{
			Session session = factory.openSession();
			
			Query query=session.createQuery("from Question where qno=:qno and subject=:subject");
			query.setParameter("qno",qno);
			query.setParameter("subject",subject);
			
			Question question=(Question) query.uniqueResult();
					
			return question;
		}
		
		
		@DeleteMapping("deleteQuestion/{qno}/{subject}")
		public boolean deleteQuestion(@PathVariable Integer qno, @PathVariable String subject)
		{
			Session session = factory.openSession();
			
			Query<Question> query=session.createQuery("from Question where qno=:qno and subject=:subject");
			query.setParameter("qno",qno);
			query.setParameter("subject",subject);
		
			Question question=query.uniqueResult();
			
			Transaction tx=session.beginTransaction();
			
				session.remove(question);
			
			tx.commit();
			
			return true;
			
		}
		
		
	}



