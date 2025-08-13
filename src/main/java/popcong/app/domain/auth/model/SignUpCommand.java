package popcong.app.domain.auth.model;

public record SignUpCommand (
        String name,
        String introduction,
        String profileImageUrl,
        Boolean userRole
) {}