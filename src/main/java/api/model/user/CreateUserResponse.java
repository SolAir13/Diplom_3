package api.model.user;

public class CreateUserResponse {
    private Boolean success;
    private UserResponse user;
    private String accessToken;
    private String refreshToken;
    private String message;

    public CreateUserResponse(Boolean success, UserResponse user, String accessToken, String refreshToken) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public CreateUserResponse() {
    }

    public CreateUserResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public UserResponse getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }
}
