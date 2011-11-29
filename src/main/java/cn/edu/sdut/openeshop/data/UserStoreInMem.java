package cn.edu.sdut.openeshop.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import cn.edu.sdut.openeshop.model.User;

@Named
@ApplicationScoped
public class UserStoreInMem implements UserStoreInf{
	private Map<String,User> userMap = new HashMap<String,User>(0);

	@Override
	public boolean userExists(String username) {
		if(userMap.containsKey(username)) return true;
		return false;
	}

	@Override
	public void addUser(User user) {
		userMap.put(user.getUsername(), user);
	}

	@Override
	public List<User> getAllUsers() {
		return (List<User>)userMap.values();
	}
}
