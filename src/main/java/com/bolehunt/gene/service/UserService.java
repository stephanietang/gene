package com.bolehunt.gene.service;

import java.util.List;

import com.bolehunt.gene.domain.Role;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.RegisterForm;
import com.bolehunt.gene.form.UpdatePasswordForm;

public interface UserService {
	
	public List<User> getAllUsers();
	
	public User getUserById(int id);
	
	public User getUserByEmail(String email);
	
	public void updateUserSelective(User user);
	
	public void insertUser(User user);
	
	public User registerUser(User user);
	
	public User enableUser(User user);
	
	public void resetUserPassword(User user, String newPassword, String encodedToken);
	
	public void updateUserPassword(User user, String newPassword);
	
	public boolean isExistingEmail(String email);
	
	public boolean isEmailPasswordMathed(String email, String password);
	
	public boolean isNewPasswordSameAsOldPassword(String email, String newPassword);
	
	public void validateRegisterForm(RegisterForm registerForm);
	
	public void validateResetPasswordForm(UpdatePasswordForm resetPasswordForm);
	
	public void validateUpdatePasswordForm(UpdatePasswordForm updatePasswordForm);
	
	public void validateSendEmailForm(RegisterForm registerForm);
	
	public List<Role> getRoleListByUser(User user);
	
	

}
