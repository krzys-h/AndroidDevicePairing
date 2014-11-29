package pl.krzysh.testprograms.dbjdbctemplate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJDBCTemplate {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("root-context.xml");

		UserDAO dao = context.getBean("userDAO", UserDAO.class);
		System.out.println("- Got UserDAO bean, starting the test...");
		
		User user = new User("test", "12345", false);
		dao.insertUser(user);
		System.out.println("- User "+user.getUserName()+" added with id="+user.getId());
		
		user.setPassword("NewPassword");
		user.setEnabled(true);
		dao.updateUser(user);
		System.out.println("- Enabled user and changed his password");

		int uid = user.getId();
		String username = user.getUserName();
		
		user = dao.selectUserById(uid);
		System.out.println("- Selected user by id "+uid+", his username is "+user.getUserName());

		user = dao.selectUserByUsername(username);
		System.out.println("- Selected user by username "+username+", his id is "+user.getId());
		
		dao.deleteUser(user);
		System.out.println("- Removed user");
		
		System.out.println("- Done!");
	}
}
