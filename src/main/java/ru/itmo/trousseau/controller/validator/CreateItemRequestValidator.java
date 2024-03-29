package ru.itmo.trousseau.controller.validator;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.trousseau.messages.CreateItemRequest;

@Component
public class CreateItemRequestValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return CreateItemRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        CreateItemRequest request = (CreateItemRequest) target;
        MultipartFile photo = request.getPhoto();
        if (photo == null || photo.isEmpty()) {
            errors.rejectValue("photo", "", "фотография должна быть прикреплена");
            return;
        }
        if (!"image/jpeg".equals(photo.getContentType())) {
            errors.rejectValue("photo", "", "поддерживается только формат image/jpeg");
            return;
        }
        if (photo.getSize() > 3 * 1024 * 1024) {
            errors.rejectValue("photo", "", "максимальный размер фотографии 3мб");
        }
    }
}
