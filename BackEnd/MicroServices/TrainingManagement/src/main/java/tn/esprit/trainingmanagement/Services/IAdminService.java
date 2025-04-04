package tn.esprit.trainingmanagement.Services;

import tn.esprit.trainingmanagement.Entity.Admin;

import java.util.List;

public interface IAdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(Long id);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Long id, Admin admin);
    boolean deleteAdmin(Long id);
}
