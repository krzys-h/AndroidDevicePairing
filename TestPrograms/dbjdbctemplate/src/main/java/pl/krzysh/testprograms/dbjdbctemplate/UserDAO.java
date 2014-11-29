package pl.krzysh.testprograms.dbjdbctemplate;

import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserDAO {
	private JdbcTemplate jdbcTemplate;

	public UserDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		initDatabase();
	}

	protected void initDatabase() {
		String initQuery = "CREATE TABLE IF NOT EXISTS `users` (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `username` VARCHAR(32) NOT NULL, `password` VARCHAR(32) NOT NULL, `enabled` BIT(1) NOT NULL)";
		jdbcTemplate.update(initQuery);
		System.out.println("Initialized tables");
	}

	public void deleteUser(User user) {
		int uid = user.getId();
		if (uid == 0) {
			System.out.println("User does not exist in the database");
			return;
		}

		String delQuery = "DELETE FROM `users` WHERE `id` = ?";
		int count = jdbcTemplate.update(delQuery, new Object[] { uid });

		if (count == 0) {
			System.out.println("User deletion failed: doesn't exist");
			return;
		}
		System.out.println("User deleted successfully.");
	}

	public void insertUser(User user) {
		if (user.getId() != 0) {
			System.out.println("User already exists in the database");
			return;
		}

		String insertQuery = "INSERT INTO `users` (`username`, `password`, `enabled`) VALUES (?, ?, ?)";
		Object[] params = new Object[] { user.getUserName(),
				user.getPassword(), user.isEnabled() };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.BIT };
		int count = jdbcTemplate.update(insertQuery, params, types);

		if (count == 0) {
			System.out.println("User insertion failed");
			return;
		}

		int uid = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()",
				Integer.class);
		user.setId(uid);

		System.out.println("Inserted user with id=" + uid);
	}

	public void updateUser(User user) {
		if (user.getId() == 0) {
			System.out.println("New user, inserting to database");
			insertUser(user);
		}

		String updateQuery = "UPDATE `users` SET `username` = ?, `password` = ?, `enabled` = ? WHERE `id` = ?";
		Object[] params = new Object[] { user.getUserName(),
				user.getPassword(), user.isEnabled(), user.getId() };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.BIT,
				Types.INTEGER };
		int count = jdbcTemplate.update(updateQuery, params, types);

		if (count == 0) {
			System.out.println("User update failed");
			return;
		}

		System.out.println("Updated user " + user.getId());
	}

	public User selectUserById(int uid) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM `users` WHERE `id` = ?", new Object[] { uid },
				new UserRowMapper());
	}

	public User selectUserByUsername(String username) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM `users` WHERE `username` = ?", new Object[] { username },
				new UserRowMapper());
	}
}
