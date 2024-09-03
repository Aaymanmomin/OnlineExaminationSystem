package com.jbk.Controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.Answer;
import com.tka.entity.Question;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpSession;

//select*

@RestController
@CrossOrigin("http://localhost:4200")
public class QuestionController {
	@Autowired
	
	SessionFactory factory;
	
	@GetMapping("getFirstQuestion/{subject}")
	public Question getFirstQuestion(@PathVariable String subject ) {
		Session session=factory.openSession();

		
		
		CriteriaBuilder cb=session.getCriteriaBuilder();
		
		CriteriaQuery<Question>cr=cb.createQuery(Question.class);
		
		Root<Question> entityobject=cr.from(Question.class);
		
		cr.select(entityobject);
		
		cr.where(cb.equal(entityobject.get("subject"),subject));
		
		Query<Question> query=session.createQuery(cr);
		
		List<Question> list=query.list();
		
		Question question=list.get(0);
		
		HttpSession httpsession=LoginController.httpsession;
		
		httpsession.setAttribute("allquestion", list);//add que in session
		return question;
	}
	
	
	
	@GetMapping("nextQuestion")
	public Question nextQuestion()
	{
	
	HttpSession httpsession=LoginController.httpsession;
	List<Question>list=(List<Question>)httpsession.getAttribute("allquestion");
	
	int index=(int)httpsession.getAttribute("questionIndex");
	
	Question question=null;
	if(index<list.size()-1) {
		
	
	int newindex=index+1;
	
	httpsession.setAttribute("questionIndex",newindex);
	
    question=list.get(newindex);
	}else {
		 question=list.get(list.size()-1);
	}
	
	return question;

}
	
	@GetMapping("previousQuestion")
	public Question previousQuestion()
	{
	
	HttpSession httpsession=LoginController.httpsession;
	List<Question>list=(List<Question>)httpsession.getAttribute("allquestion");
	
	int index=(int)httpsession.getAttribute("questionIndex");
	
	Question question=null;
	
	if(index>0) {
		
	
	int newindex=index-1;
	
	httpsession.setAttribute("questionIndex",newindex);
	
    question=list.get(newindex);
	}
	else
	{
		 question=list.get(0);
	}
	
	return question;
}
	@PostMapping("saveAnswer")
	public void saveAnswer(@RequestBody Answer answer)
	{

		HttpSession httpSession=LoginController.httpsession;
		
		HashMap<Integer,Answer> hashmap=(HashMap<Integer, Answer>) httpSession.getAttribute("submittedDetails");
		
		hashmap.put(answer.qno,answer);
		
		System.out.println(hashmap);
		
	}

	@GetMapping("getAllSubjects")
	public List<String> getAllSubjects()
	{
		
		Session session=factory.openSession();
		
		Query query=session.createQuery("select distinct subject from Question");
		
		List<String> list=query.list();
		
		return list;
				
	}
	
	
	@GetMapping("getAllQno/{subject}")
	public List<Integer> getAllQno(@PathVariable String subject)
	{
		
		Session session=factory.openSession();
		
		Query query=session.createQuery("select distinct qno from Question where subject=:subject");
		
		query.setParameter("subject",subject);
		
		List<Integer> list=query.list();
		
		return list;
				
	}
	
	
	

	@GetMapping("getQuestion/{questionNumber}")
	public Question getQuestion(@PathVariable int questionNumber)
	{
		HttpSession httpSession=LoginController.httpsession;
		
		List<Question> listofquestions=(List<Question>) httpSession.getAttribute("allquestion");
		
		Question expectedQuestion=null;
		
		for (Question question : listofquestions) 
		{
			if(question.qno==questionNumber)
			{
				expectedQuestion=question;
			}
		}
		
		return expectedQuestion;
		
	}
	
	
	
	@GetMapping("calculateScore")
	
	public Integer calculateScore()
	{
		HttpSession httpSession=LoginController.httpsession;
		
		HashMap<Integer,Answer> hashmap=(HashMap<Integer, Answer>) httpSession.getAttribute("submittedDetails");
		
		
		Collection<Answer> collection=hashmap.values();
        
		httpSession.setAttribute("score",0);
		
		for (Answer answer : collection) 
		{
					if(answer.submittedAnswer.equals(answer.correctAnswer))
					{

						httpSession.setAttribute("score",(int)httpSession.getAttribute("score")+1);
						
					}
		}
		
		int score=(int)httpSession.getAttribute("score");
		
		//httpSession.invalidate();// it will remove all attributes from HttpSession object .
		
		
		return score;
		
		
		
	}
	
@GetMapping("allAnswers")
	
	public Collection<Answer> getAllAnswers()
	{
		HttpSession httpSession=LoginController.httpsession;
		
		HashMap<Integer,Answer> hashmap=(HashMap<Integer, Answer>) httpSession.getAttribute("submittedDetails");
		
		
		Collection<Answer> allanswers=hashmap.values();
		return allanswers;

}


}
//session null means validate call once 