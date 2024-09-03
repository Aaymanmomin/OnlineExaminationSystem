package com.jbk.Controller;



	import java.util.List;

	import org.hibernate.Session;
	import org.hibernate.SessionFactory;
	import org.hibernate.Transaction;
	import org.hibernate.query.Query;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.web.bind.annotation.CrossOrigin;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	import com.tka.entity.Result;

	@RestController
	@CrossOrigin("http://localhost:4200")
	public class ResultController 
	{
		@Autowired
		SessionFactory factory;
		
		//{"username":"raina","subject":"java","score":"9"}
		@RequestMapping("saveResult")
		public void saveResult(@RequestBody Result result)
		{
			System.out.println(result);
			
			Session session=factory.openSession();
			
			Transaction tx=session.beginTransaction();
				
					session.persist(result);
			
			tx.commit();
		}
		
		@RequestMapping("getResults/{subject}")
		public List<Result> getResults(@PathVariable String subject)
		{
			Session session=factory.openSession();
			
			Query query=session.createQuery("from Result where subject=:subject");
			
			query.setParameter("subject", subject);
			
			List<Result> list =query.list();
			
			return list;
		}
		
		@RequestMapping("getRecordsCounts/{subject}")
		public int getRecordsCounts(@PathVariable String subject)
		{
			Session session=factory.openSession();
			
			Query query=session.createQuery("from Result where subject=:subject");
			
			query.setParameter("subject", subject);
			
			List<Result> list =query.list();
			
			return list.size();
					
		}
		
		
		
		@RequestMapping("getResults2/{subject}/{pageno}")
		public List<Result> getResults2(@PathVariable String subject,@PathVariable int pageno)
		{
			System.out.println("pageno is " + pageno);
			
			Session session=factory.openSession();
			
			Query<Long> query=session.createQuery("select count('subject') from Result where subject=:subject",Long.class);
		
			query.setParameter("subject",subject);
			
			Long noofrecords = query.uniqueResult();
			
			int pagenumber=1;//4
			
			// 3*3=12< 11
			
			while(3*pagenumber<noofrecords)
			{
				pagenumber+=1;
			}
			//i=4 count=9
			
			int[] indexarray= new int[pagenumber];// [0,3,6,9] 
						       //    0 1 2 3
			
			for (int i = 0 , count=0 ; i < indexarray.length; i++,count=count+3) 
			{
				indexarray[i]=count;
			}
			
			Query<Result> query2=session.createQuery("from Result where subject=:subject");
			
			query2.setParameter("subject",subject);
			
			int startindex=indexarray[pageno-1]; // [0,3,6,9]
			
			query2.setMaxResults(3);
			query2.setFirstResult(startindex);
			
			List<Result> list=query2.list();
			
			return list;
		}
		
		
		
		
		
		
		
		
	}

