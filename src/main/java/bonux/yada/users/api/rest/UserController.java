package bonux.yada.users.api.rest;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bonux.yada.users.services.UserServices;

@RestController
@RequestMapping("/yada")
class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserServices userServices;

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	void handle(Exception e) {
		logger.error(e.getMessage(), e);
	}

	@GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Secured({ "ADMIN" })
	HttpEntity<?> readAll() {
		logger.info("reading all users");

		return ResponseEntity.ok(userServices.streamAll()
//				.peek(user -> user.add(linkTo(methodOn(UserController.class).getSome()).slash(todo.id).withSelfRel()))
				.collect(Collectors.toList()));
	}

}
