# Android Development Guidelines and Standards

This document serves as a reference for maintaining consistent development practices across the FlexiBank Android application.

## Architecture Guidelines

- **Pattern**: MVVM (Model-View-ViewModel)
- **UI Framework**: Jetpack Compose
- **Programming Language**: Kotlin

## Clean Code Principles

- **SOLID**
  - Single Responsibility: Each class should have only one reason to change
  - Open-Closed: Open for extension, closed for modification
  - Liskov Substitution: Subtypes must be substitutable for their base types
  - Interface Segregation: Many specific interfaces better than one general
  - Dependency Inversion: Depend on abstractions, not concretions

- **KISS**: Keep It Simple, Stupid
  - Avoid unnecessary complexity
  - Prioritize readability and maintainability
  - Choose simple solutions over clever ones

- **DRY**: Don't Repeat Yourself
  - Extract reusable code into functions/classes
  - Create shared utilities for common operations
  - Use inheritance and composition appropriately

## Dependency Injection

- **Framework**: Hilt
- Use @Inject for constructor injection when possible
- Create modules for providing dependencies that cannot be constructor-injected
- Use appropriate scopes (@Singleton, @ActivityScoped, etc.)

## Naming Conventions

- **Functions/Variables**: camelCase
  - Example: `getUserData()`, `userProfileData`

- **Classes**: PascalCase
  - Example: `UserRepository`, `LoginViewModel`

- **Constants**: ALL_CAPS
  - Example: `MAX_RETRY_COUNT`, `API_BASE_URL`

## Code Documentation Requirements

- Add comments explaining the purpose of complex code blocks
- Include all required imports in code examples
- Document alternative approaches with pros/cons when applicable
- Use KDoc format for function/class documentation

## Code Structure Guidelines

- Provide clear and minimal examples
- Design with scalability in mind
- Create modular components that can be reused
- Offer code snippets rather than full project changes unless requested

## UI Development

- Use Jetpack Compose for new UI components
- Follow Material Design 3 guidelines
- Create reusable composable functions
- Apply appropriate theming and dynamic colors
- Ensure accessibility support (content descriptions, appropriate contrast)

## Testing

- Write unit tests for ViewModels and repositories
- Use UI tests for critical user flows
- Implement integration tests for API interactions
- Mock dependencies for isolated testing
