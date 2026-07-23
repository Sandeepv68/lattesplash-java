---
name: Bug Report
about: Report a bug to help us improve LatteSplash
title: '[BUG] '
labels: bug
assignees: ''
---

## Describe the Bug

A clear and concise description of what the bug is.

## To Reproduce

Steps to reproduce the behavior:

1. Initialize LatteSplash with '...'
2. Call method '...'
3. See error

## Expected Behavior

A clear and concise description of what you expected to happen.

## Screenshots

If applicable, add screenshots to help explain your problem.

## Environment

- **Java Version**: [e.g., Java 11, 17, 21]
- **OS**: [e.g., Windows 10, macOS 12, Ubuntu 20.04]
- **LatteSplash Version**: [e.g., 1.0.0]
- **Build Tool**: [e.g., Maven 3.9.6, Gradle 7.5]

## API Details

- **Endpoint**: [e.g., GET /photos/:id]
- **HTTP Method**: [e.g., GET, POST, PUT, DELETE]
- **Authentication**: [e.g., Bearer Token, Client-ID]

## Code Sample

```java
// Paste your code here
LatteSplashConfig config = new LatteSplashConfig.Builder()
    .bearerToken("<token>")
    .build();

LatteSplash unsplash = new LatteSplash(config);
// Code that causes the issue
```

## Error Output

```
Paste the full stack trace or error message here
```

## Additional Context

Add any other context about the problem here. For example:
- Does this happen every time, or only sometimes?
- Did this work before? When did it stop working?
- Any workarounds you've found?
