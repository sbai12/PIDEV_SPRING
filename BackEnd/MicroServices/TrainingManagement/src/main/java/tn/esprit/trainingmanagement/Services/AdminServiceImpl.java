package tn.esprit.trainingmanagement.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.Admin;
import tn.esprit.trainingmanagement.Repository.AdminRepo;

import java.util.List;
import java.util.Optional;

@Service

public class AdminServiceImpl implements IAdminService{


    @Autowired
    private AdminRepo adminRepo;

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    @Override
    public Admin getAdminById(Long id) {
        Optional<Admin> admin = adminRepo.findById(id);
        return admin.orElse(null);    }

    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    @Override
    public Admin updateAdmin(Long id, Admin admin) {
        if (adminRepo.existsById(id)) {
            admin.setId(id);  // Mettre à jour l'ID de l'admin
            return adminRepo.save(admin);
        } else {
            return null;
        }    }

    @Override
    public boolean deleteAdmin(Long id) {
        if (adminRepo.existsById(id)) {
            adminRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
