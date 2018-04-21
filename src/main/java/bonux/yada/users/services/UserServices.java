package bonux.yada.users.services;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bonux.yada.users.model.User;
import bonux.yada.users.repo.UserRepo;

public interface UserServices {
	// C
	// R
	Stream<User> streamAll();
	// U
	// D
}

@Service
class UserServicesImpl implements UserServices {
	@Autowired
	private UserRepo userRepo;

	@Override
    public Stream<User> streamAll() {
        return userRepo.findAll().stream();
    }
}