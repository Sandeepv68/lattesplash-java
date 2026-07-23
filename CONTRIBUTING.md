# Contributing to LatteSplash

Thank you for your interest in contributing to LatteSplash! This document provides guidelines and steps for contributing.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Making Changes](#making-changes)
- [Testing](#testing)
- [Pull Request Process](#pull-request-process)
- [Coding Standards](#coding-standards)
- [Reporting Bugs](#reporting-bugs)
- [Requesting Features](#requesting-features)

## Code of Conduct

This project and everyone participating in it is governed by our [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. Please report unacceptable behavior to the project maintainer.

## Getting Started

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```sh
   git clone https://github.com/YOUR_USERNAME/lattesplash-java.git
   cd lattesplash-java
   ```
3. Add the upstream remote:
   ```sh
   git remote add upstream https://github.com/SandeepVattapparambil/lattesplash-java.git
   ```
4. Create a feature branch:
   ```sh
   git checkout -b feature/your-feature-name
   ```

## Development Setup

### Prerequisites

- Java 11 or higher (JDK)
- Maven 3.6 or higher
- Git

### Building the Project

```sh
# Compile the project
mvn compile

# Run all tests
mvn test

# Build the JAR package
mvn package

# Generate Javadoc
mvn javadoc:javadoc
```

### IDE Setup

- **IntelliJ IDEA**: Import as Maven project
- **Eclipse**: Import Existing Maven Projects
- **VS Code**: Install Java Extension Pack

## Making Changes

1. **Create a feature branch** from `main`:
   ```sh
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes** following the coding standards below

3. **Write or update tests** for your changes

4. **Run the full test suite** to ensure nothing is broken:
   ```sh
   mvn test
   ```

5. **Commit your changes** with a clear, descriptive message:
   ```sh
   git commit -m "feat: add new feature description"
   ```

6. **Push to your fork**:
   ```sh
   git push origin feature/your-feature-name
   ```

7. **Create a Pull Request** on GitHub

## Testing

### Running Tests

```sh
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=LatteSplashTest

# Run specific test method
mvn test -Dtest=LatteSplashTest#testGetPhoto
```

### Writing Tests

- All new features must include tests
- Tests should be in `src/test/java/com/lattesplash/`
- Use JUnit 5 annotations: `@Test`, `@BeforeEach`, `@AfterEach`
- Follow the existing test patterns in the codebase

### Test Structure

```java
package com.lattesplash;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class YourFeatureTest {
    
    @BeforeEach
    void setUp() {
        // Setup test fixtures
    }
    
    @Test
    void testFeatureBehavior() {
        // Arrange
        // Act
        // Assert
    }
}
```

## Pull Request Process

1. **Update documentation** if your change affects public API
2. **Add CHANGELOG entry** describing your change
3. **Ensure CI passes** - all tests must pass
4. **Request review** from maintainers
5. **Address feedback** promptly

### PR Title Format

Use conventional commit format:
- `feat: add new feature`
- `fix: resolve bug in X`
- `docs: update documentation`
- `test: add tests for Y`
- `refactor: improve Z`
- `chore: update dependencies`

### PR Description Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests added/updated
- [ ] All tests pass locally

## Checklist
- [ ] Code follows project style
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No breaking changes (or documented)
```

## Coding Standards

### Java Style

- Follow [Oracle Java Code Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-background.html)
- Use meaningful variable and method names
- Keep methods focused and concise
- Add Javadoc for public APIs

### Naming Conventions

- **Classes**: PascalCase (`LatteSplash`, `QueryParams`)
- **Methods**: camelCase (`getPhoto`, `listCollections`)
- **Constants**: UPPER_SNAKE_CASE (`BASE_URL`)
- **Packages**: lowercase (`com.lattesplash.api`)

### Code Organization

```java
// 1. Package statement
package com.lattesplash;

// 2. Imports (alphabetical order)
import java.util.List;
import java.util.concurrent.CompletableFuture;

// 3. Class declaration
public class Example {
    
    // 4. Constants
    private static final String CONSTANT = "value";
    
    // 5. Fields
    private final String fieldName;
    
    // 6. Constructors
    public Example(String value) {
        this.fieldName = value;
    }
    
    // 7. Public methods
    public String getFieldName() {
        return fieldName;
    }
    
    // 8. Private methods
    private void helperMethod() {
        // implementation
    }
}
```

### Error Handling

- Use checked exceptions (`LatteSplashError`) for API errors
- Provide meaningful error messages
- Don't swallow exceptions silently

```java
// Good
try {
    return httpClient.execute(request);
} catch (IOException e) {
    throw new LatteSplashError("Failed to execute request: " + e.getMessage(), e);
}

// Bad
try {
    return httpClient.execute(request);
} catch (IOException e) {
    return null; // Don't do this
}
```

## Reporting Bugs

### Bug Report Template

```markdown
**Describe the bug**
A clear description of the bug

**To Reproduce**
Steps to reproduce the behavior

**Expected behavior**
What you expected to happen

**Screenshots**
If applicable, add screenshots

**Environment**
- Java version:
- OS:
- Library version:

**Additional context**
Any other information
```

### Where to Report

- GitHub Issues: https://github.com/SandeepVattapparambil/lattesplash-java/issues

## Requesting Features

### Feature Request Template

```markdown
**Is your feature request related to a problem?**
A clear description of the problem

**Describe the solution you'd like**
What you want to happen

**Describe alternatives you've considered**
Other solutions you've thought about

**Additional context**
Any other information, mockups, or examples
```

## Questions?

If you have questions about contributing, feel free to open an issue with the label "question" or reach out to the maintainers.

## License

By contributing to LatteSplash, you agree that your contributions will be licensed under the [MIT License](LICENSE).
