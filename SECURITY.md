# Security Policy

## Reporting a Vulnerability

If you discover a security vulnerability within LatteSplash, please send an email to the project maintainer. All security vulnerabilities will be promptly addressed.

**Please do not report security vulnerabilities through public GitHub issues.**

## Supported Versions

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |

## Security Measures

### Authentication

- **Bearer Token**: Store tokens securely; never commit to version control
- **Client-ID**: Keep your API key private; use environment variables
- **OAuth2**: Implement proper token refresh mechanisms

### Best Practices

1. **Environment Variables**: Store sensitive credentials in environment variables:
   ```java
   String apiKey = System.getenv("UNSPLASH_ACCESS_KEY");
   ```

2. **Secure Storage**: Use a secrets manager for production applications (AWS Secrets Manager, HashiCorp Vault, etc.)

3. **HTTPS Only**: All API communication uses HTTPS by default

4. **Token Rotation**: Regularly rotate your API keys and tokens

5. **Minimal Permissions**: Request only the scopes you need

### Request Security

- All requests use HTTPS
- SHA-256 header hashing for request verification
- Configurable timeout to prevent hanging connections
- Automatic retry with exponential backoff

### Data Handling

- API responses are not cached by default
- Sensitive data is not logged
- Credentials are masked in debug output

## Dependencies

LatteSplash uses the following dependencies with known security track records:

| Dependency | Version | Purpose |
|------------|---------|---------|
| OkHttp | 4.12.0 | HTTP client |
| Gson | 2.10.1 | JSON serialization |
| SLF4J | 2.0.9 | Logging |

### Updating Dependencies

Regularly check for dependency updates:
```sh
mvn versions:display-dependency-updates
```

## API Security Guidelines

### Unsplash API Security

1. **Rate Limiting**: Respect the Unsplash API rate limits (50 requests/hour for demo apps, 5000 for production)
2. **Caching**: Implement client-side caching to reduce API calls
3. **Error Handling**: Don't expose API keys in error messages
4. **Logging**: Never log sensitive credentials

### Example: Secure Configuration

```java
// Good: Using environment variables
LatteSplashConfig config = new LatteSplashConfig.Builder()
    .accessKey(System.getenv("UNSPLASH_ACCESS_KEY"))
    .secretKey(System.getenv("UNSPLASH_SECRET_KEY"))
    .build();

// Bad: Hardcoded credentials (NEVER DO THIS)
LatteSplashConfig config = new LatteSplashConfig.Builder()
    .accessKey("your-access-key-here")  // DON'T
    .build();
```

## Security Updates

Security updates will be released as patch versions (e.g., 1.0.1, 1.0.2).

Subscribe to GitHub releases for notifications.

## Contact

For security-related inquiries, please contact the project maintainer via:
- GitHub: https://github.com/SandeepVattapparambil
- Email: [Contact via GitHub profile]

## Acknowledgments

We thank the security research community for responsibly disclosing vulnerabilities.

## License

This security policy is part of the LatteSplash project, licensed under the [MIT License](LICENSE).
