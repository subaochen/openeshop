package cn.edu.sdut.openeshop.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import cn.edu.sdut.openeshop.model.Member;

@Named
@ApplicationScoped
public class UserStoreInMem implements UserStoreInf{
	private Map<String,Member> userMap = new HashMap<String,Member>(0);

	@Override
	public boolean userExists(String username) {
		if(userMap.containsKey(username)) return true;
		return false;
	}

	@Override
	public void addUser(Member user) {
		userMap.put(user.getUsername(), user);
	}

	@Override
	public List<Member> getAllUsers() {
		List<Member> list = new ArrayList<Member>(0);
	    list.addAll(userMap.values());
		return list;
	}

	@Override
	public Member findUser(String username) {
		return userMap.get(username);
	}
}
