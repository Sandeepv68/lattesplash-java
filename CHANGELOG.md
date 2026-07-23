# Changelog

All notable changes to LatteSplash will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-01-01

### Added

- Initial release of LatteSplash Java library
- Complete port of wrapsplash npm module (v5.0.0)
- Support for all 34 Unsplash API endpoints
- Synchronous and asynchronous (`CompletableFuture`) API variants
- Builder pattern configuration (`LatteSplashConfig.Builder`)
- OkHttp-based HTTP client with retry support
- JSON serialization/deserialization using Gson
- SLF4J logging integration
- Bearer token and Client-ID authentication
- Input validation for all API methods
- SHA-256 header hashing for security
- Configurable timeout and retry settings
- Automatic retry with configurable delay
- Error normalization with `LatteSplashError` exception
- 204 Content Deleted handling
- 403 Rate Limit handling
- Comprehensive JUnit 5 test suite (95 tests)
- Full Javadoc documentation
- Maven and Gradle dependency support
- GitHub Actions CI/CD workflow
- GitHub Packages publishing workflow

### Features

- **Users API**: Get user profiles, photos, likes, collections, statistics
- **Photos API**: List, get, update, like, unlike photos; get statistics and download links
- **Search API**: Search photos, collections, and users
- **Collections API**: Full CRUD operations for collections
- **Current User API**: Get and update current user profile
- **Stats API**: Get platform statistics totals and monthly stats

### Authentication

- Bearer token authentication
- Client-ID authentication
- OAuth2 support with bearer token generation

### Configuration

- Builder pattern for immutable configuration
- Configurable timeout (default: 10000ms)
- Configurable retry count (default: 2)
- Configurable retry delay (default: 100ms)

### Error Handling

- Checked exception model (`LatteSplashError`)
- Automatic error normalization
- Rate limit detection and handling
- Content deletion (204) handling

### Testing

- Unit tests for all API classes
- Integration tests with MockHttpClient
- Validation tests for input parameters
- Async operation tests
- Error handling tests
- SHA-256 hashing tests

### Documentation

- Comprehensive README with usage examples
- Full Javadoc for all public APIs
- API reference documentation
- Contributing guidelines
- Code of conduct
- Security policy
- Changelog

## [Unreleased]

### Planned

- Maven Central publishing
- Additional logging adapters
- Connection pooling optimization
- Retry-after header support
- Rate limit headers parsing

---

## Versioning

- **Major**: Breaking changes to public API
- **Minor**: New features, backward compatible
- **Patch**: Bug fixes, backward compatible

## Support

- Java 11+ required
- GitHub Issues: https://github.com/Sandeepv68/lattesplash-java/issues
