package hello;

import org.ramon.dao.BooksDaoImpl;
import org.ramon.model.Author;
import org.ramon.model.Book;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ApplicationContext context = new ClassPathXmlApplicationContext("org/ramon/xmlfiles/mapping.xml");
		//BooksDaoImpl daobook= (BooksDaoImpl)context.getBean("mapbooks");
		//System.out.println(daobook.getList("asd").size());
		//System.out.println(daobook.getList("asd").get(0).getAuthor().getName());
		//daobook.updateBook("1", "jose");
		//Book b = daobook.getBook("1");
		//System.out.println(b.getAuthor().getName());
		//System.out.println(daobook.getList("Gabriel").size());
		//BooksDaoImpl daobook
		//System.out.println("Initial Size Map: "+daobook.getBooks().size());
		//Author au=null;
		//daobook.addBook(new Book("asd","asd",au),new Author("asddd","sdsds"));
		//System.out.println("Initial Size Map: "+daobook.getBooks().size());
	    //for (int i = 0; i < daobook.getAllBooks().size(); i++) {
			//System.out.println(daobook.getAllBooks().get(i).getAuthor().getName());
		//}
		
		BooksDaoImpl b=new BooksDaoImpl();
		//b.addBook(new Book(),new Author());
		System.out.println(b.getAllBooks().get(0).getName());
		Book b1=b.getAllBooks().get(0);
		b1.setName("Maria");
		b.updateBook(b1);
		System.out.println(b.getAllBooks().get(0).getName());
		
	}

}
