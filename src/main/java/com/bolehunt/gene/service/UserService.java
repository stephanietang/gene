package com.bolehunt.gene.service;

import java.util.List;

import com.bolehunt.gene.common.JsonResponse;
import com.bolehunt.gene.domain.User;
import com.bolehunt.gene.form.BaseForm;
import com.bolehunt.gene.form.LoginForm;
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
	
	public BaseForm initBaseForm();
	
	public boolean isExistingEmail(String email);
	
	public boolean isEmailPasswordMathed(String email, String password);
	
	public boolean isNewPasswordSameAsOldPassword(String email, String newPassword);
	
	public JsonResponse validateRegisterForm(RegisterForm registerForm);
	
	public JsonResponse validateLoginForm(LoginForm loginForm);
	
	public JsonResponse validateResetPasswordForm(UpdatePasswordForm resetPasswordForm);
	
	public JsonResponse validateUpdatePasswordForm(UpdatePasswordForm updatePasswordForm);
	
	public JsonResponse validateSendEmailForm(RegisterForm registerForm);

}
