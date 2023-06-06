package com.pragma.powerup.usermicroservice.domain.model;

public class ConstantsUseCase {
    private ConstantsUseCase()  {
        throw new IllegalStateException("Utility class");
    }

    public final static String ADMINISTRATOR_ROLE = "ADMINISTRATOR_ROLE";
    public final static String PROVIDER_ROLE = "PROVIDER_ROLE";
    public final static String EMPLOYEE_ROLE = "EMPLOYEE_ROLE";
    public final static Long ID_CLIENT_REQUESTDTO = 1L;
    public final static Long ID_EMPLOYEE_REQUESTDTO = 2L;
    public final static Long ID_PROVIDER_REQUESTDTO = 3L;
    public final static Long ID_ADMINISTRATOR_ROLE = 4L;
    public final static String AGE_NOT_VALID_PROVIDER = "Birthdate is required and the owner must be 18 years old or more.";
    public final static String UNAUTHORIZED_ACCESS_EXCEPTION = "User is not authorized.";
    public final static String RESPOSNE_ERROR_MESSAGE_KEY = "error";

}
