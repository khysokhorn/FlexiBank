# Project Structure Guidelines

This document outlines the recommended package structure and organization for maintaining a clean, scalable architecture in the FlexiBank Android application.

## Package Structure

The root package `com.nexgen.flexiBank` should be organized as follows:

| Package | Purpose |
|---------|--------|
| `model` | Data models and entities |
| `repository` | Data repositories |
| `network` | API interfaces and network-related classes |
| `module` | Feature modules organized by functionality |
| `viewmodel` | ViewModels for MVVM architecture |
| `utils` | Utility classes and extensions |
| `common` | Common/shared components |
| `component` | Reusable UI components |

## Feature Module Structure

Within the `module` package, each feature should be organized as follows:

```
module/
  ├── featureA/
  │     ├── view/         # UI components (Fragments, Activities, Composables)
  │     ├── viewmodel/    # ViewModels specific to this feature
  │     ├── repository/   # Repositories specific to this feature
  │     └── model/        # Data models specific to this feature
  │
  ├── featureB/
  │     ├── view/
  │     ├── viewmodel/
  │     ├── repository/
  │     └── model/
  │
  └── ...
```

## Resource Organization

The `res` directory should be organized as follows:

```
res/
  ├── layout/       # XML layouts
  ├── drawable/     # Images and drawable resources
  ├── values/       # Strings, colors, styles, dimensions
  │     ├── strings.xml
  │     ├── colors.xml
  │     ├── themes.xml
  │     └── dimens.xml
  ├── navigation/   # Navigation graphs
  ├── font/         # Custom fonts
  └── ...
```

## Module Organization for Larger Projects

For larger projects, consider splitting the app into modules:

| Module | Purpose |
|--------|--------|
| `app` | Main application module |
| `core` | Core functionality and utilities |
| Feature modules | Separate modules for each major feature |
| `design-system` | UI components and theming |
| `data` | Data layer and repositories |
| `domain` | Business logic and use cases |

## File Naming Conventions

| File Type | Naming Convention | Example |
|-----------|-------------------|--------|
| Activities | `[Feature]Activity.kt` | `LoginActivity.kt` |
| Fragments | `[Feature]Fragment.kt` | `ProfileFragment.kt` |
| ViewModels | `[Feature]ViewModel.kt` | `AuthViewModel.kt` |
| Adapters | `[Feature]Adapter.kt` | `TransactionAdapter.kt` |
| Repositories | `[Feature]Repository.kt` | `UserRepository.kt` |
| Composables | `[Feature]Screen.kt` or `[Feature]Component.kt` | `LoginScreen.kt` |
| Utility classes | `[Functionality]Utils.kt` or `[Functionality]Extensions.kt` | `DateUtils.kt` |

## Example Directory Structure

```
com.nexgen.flexiBank/
  ├── AppApplication.kt
  ├── model/
  │     ├── User.kt
  │     ├── Account.kt
  │     └── Transaction.kt
  ├── repository/
  │     ├── UserRepository.kt
  │     ├── AccountRepository.kt
  │     └── TransactionRepository.kt
  ├── network/
  │     ├── ApiInterface.kt
  │     ├── ApiResult.kt
  │     └── interceptor/
  │           └── AuthInterceptor.kt
  ├── module/
  │     ├── auth/
  │     │     ├── view/
  │     │     │     ├── LoginFragment.kt
  │     │     │     └── RegisterFragment.kt
  │     │     ├── viewmodel/
  │     │     │     └── AuthViewModel.kt
  │     │     └── model/
  │     │           └── LoginRequest.kt
  │     └── dashboard/
  │           ├── view/
  │           │     └── DashboardFragment.kt
  │           └── viewmodel/
  │                 └── DashboardViewModel.kt
  ├── viewmodel/
  │     └── BaseViewModel.kt
  ├── utils/
  │     ├── DateUtils.kt
  │     ├── CurrencyFormatter.kt
  │     └── theme/
  │           ├── Color.kt
  │           └── Theme.kt
  ├── common/
  │     ├── BaseFragment.kt
  │     └── BaseActivity.kt
  └── component/
        ├── LoadingButton.kt
        └── ProfileAvatar.kt
```

## Navigation

Use the Navigation Component with a single Activity and multiple Fragment approach:

```xml
<!-- Example navigation graph structure -->
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_graph"
    app:startDestination="@id/splashFragment">

    <fragment
        android:id="@+id/splashFragment"
        android:name="com.nexgen.flexiBank.module.splash.view.SplashFragment">
        <action
            android:id="@+id/action_splash_to_login"
            app:destination="@id/loginFragment" />
        <action
            android:id="@+id/action_splash_to_dashboard"
            app:destination="@id/dashboardFragment" />
    </fragment>

    <fragment
        android:id="@+id/loginFragment"
        android:name="com.nexgen.flexiBank.module.auth.view.LoginFragment">
        <action
            android:id="@+id/action_login_to_register"
            app:destination="@id/registerFragment" />
        <action
            android:id="@+id/action_login_to_dashboard"
            app:destination="@id/dashboardFragment" />
    </fragment>

    <!-- Additional fragments and actions -->

</navigation>
```
