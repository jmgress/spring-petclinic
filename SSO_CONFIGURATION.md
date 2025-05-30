# SSO Integration for Spring PetClinic

This application now includes Single Sign-On (SSO) integration using OAuth2/OpenID Connect.

## Configuration

To configure SSO for your organization, set the following environment variables or application properties:

### Required Environment Variables

```bash
# OAuth2 Client Configuration
export SSO_CLIENT_ID="your-client-id"
export SSO_CLIENT_SECRET="your-client-secret"

# OAuth2 Provider URLs
export SSO_AUTHORIZATION_URI="https://your-sso-provider.com/oauth/authorize"
export SSO_TOKEN_URI="https://your-sso-provider.com/oauth/token"
export SSO_USER_INFO_URI="https://your-sso-provider.com/oauth/userinfo"
export SSO_JWK_SET_URI="https://your-sso-provider.com/.well-known/jwks.json"
```

### For Popular Providers

#### Azure AD / Entra ID
```bash
export SSO_AUTHORIZATION_URI="https://login.microsoftonline.com/{tenant-id}/oauth2/v2.0/authorize"
export SSO_TOKEN_URI="https://login.microsoftonline.com/{tenant-id}/oauth2/v2.0/token"
export SSO_USER_INFO_URI="https://graph.microsoft.com/oidc/userinfo"
export SSO_JWK_SET_URI="https://login.microsoftonline.com/{tenant-id}/discovery/v2.0/keys"
```

#### Google Workspace
```bash
export SSO_AUTHORIZATION_URI="https://accounts.google.com/o/oauth2/v2/auth"
export SSO_TOKEN_URI="https://oauth2.googleapis.com/token"
export SSO_USER_INFO_URI="https://openidconnect.googleapis.com/v1/userinfo"
export SSO_JWK_SET_URI="https://www.googleapis.com/oauth2/v3/certs"
```

#### Okta
```bash
export SSO_AUTHORIZATION_URI="https://{your-domain}.okta.com/oauth2/default/v1/authorize"
export SSO_TOKEN_URI="https://{your-domain}.okta.com/oauth2/default/v1/token"
export SSO_USER_INFO_URI="https://{your-domain}.okta.com/oauth2/default/v1/userinfo"
export SSO_JWK_SET_URI="https://{your-domain}.okta.com/oauth2/default/v1/keys"
```

## Security Model

The application uses a practical security model:

- **Public Access**: Home page, vet listings, owner details (read-only)
- **Authenticated Access Required**: Creating/editing owners, pets, and visits

### Protected Endpoints

- `/owners/new` - Create new owner
- `/owners/*/edit` - Edit existing owner
- `/owners/*/pets/new` - Add new pet
- `/owners/*/pets/*/edit` - Edit existing pet
- `/owners/*/pets/*/visits/new` - Add new visit

## Login/Logout

- **Login**: Click the "Login" button in the navigation bar
- **Logout**: When authenticated, click your username and select "Logout"

## Development

For development/testing without a real SSO provider, the application will use demo values. Configure a real OAuth2 provider for production use.