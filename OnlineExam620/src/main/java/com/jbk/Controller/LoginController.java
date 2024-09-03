package com.jbk.Controller;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.Admin;
import com.tka.entity.Answer;
import com.tka.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController 
@CrossOrigin("http://localhost:4200")
public class LoginController {
	@Autowired 
	SessionFactory factory;///ref var factory for SessionFactory
	static HttpSession httpsession;


	@PostMapping("validate")//student
	public boolean validate(@RequestBody User userfromBrowser,HttpServletRequest request){//HttpServletRequest its container to store browser obj {

        
		httpsession=request.getSession();//httpsession methods are getSession nd openSession
		
		Session session =factory.openSession();

		
		User userfromdb=session.get(User.class,userfromBrowser.getUsername());

		boolean answer=userfromBrowser.equals(userfromdb);
       
		if(answer) {
			
			
    	   httpsession.setAttribute("score",0);////score always calculate from 0
    	   
    	   httpsession.setAttribute("questionIndex",0);
    	   
  	   HashMap<Integer,Answer>hashmap=new HashMap<Integer,Answer>();
  	   httpsession.setAttribute("submittedDetails", hashmap);
      }
		return answer;

	}
	
	@PostMapping("validate2")//for admin user
	public Boolean validate2(@RequestBody Admin userfrombrowser,HttpServletRequest request)
	{
		System.out.println("user from browser " + userfrombrowser);
	
		Session session=factory.openSession();
		
		Admin userfromdatabase=session.get(Admin.class,userfrombrowser.getUsername());
		
		System.out.println("user from database " +userfromdatabase);
					
		// this refers to that object which is used to call method
		
		if(userfromdatabase==null)
		{
			return false;
		}
		
		boolean answer=userfrombrowser.equals(userfromdatabase);
				
		System.out.println("answer from equals() of Object class is " + answer);
			
		return answer;
				
	}



}
