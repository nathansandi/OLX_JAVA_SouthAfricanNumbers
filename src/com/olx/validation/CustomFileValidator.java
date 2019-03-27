package com.olx.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.olx.models.FileUploadModel;

@Component
public class CustomFileValidator implements Validator{
public static final String CSV_MIME_TYPE="application/vnd.ms-excel";
public static final long TEN_MB_IN_BYTES = 10485760;
	@Override
	public boolean supports(Class<?> clazz) {
		return FileUploadModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FileUploadModel fileUploadModel = (FileUploadModel)target;
		MultipartFile file = fileUploadModel.getFile();
		if(file.isEmpty()){
			errors.rejectValue("file", "upload.file.required");
		}
		else if(!CSV_MIME_TYPE.equalsIgnoreCase(file.getContentType())){
			errors.rejectValue("file", "upload.invalid.file.type");
		}
		
		else if(file.getSize() > TEN_MB_IN_BYTES){
			errors.rejectValue("file", "upload.exceeded.file.size");
		}
		
	}

}
