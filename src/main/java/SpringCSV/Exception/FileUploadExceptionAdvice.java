package SpringCSV.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import SpringCSV.model.ResponeMessage;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler{

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity handlemaxSizeException(MaxUploadSizeExceededException ex) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponeMessage("File too large",""));
	}
	
	
}
