# Android Development Best Practices

This document outlines recommended best practices for Android development in the FlexiBank application.

## General Principles

### Performance

- Avoid blocking the main thread
- Use ViewModels to survive configuration changes
- Implement pagination for large data sets
- Use efficient data structures and algorithms
- Optimize layouts (flatten hierarchy, use ConstraintLayout)
- Minimize resource usage (memory, battery, network)

### Security

- Store sensitive data in EncryptedSharedPreferences
- Use HTTPS for all network communication
- Implement proper authentication and authorization
- Apply content security policies
- Validate all user inputs
- Implement certificate pinning for critical APIs
- Don't store sensitive data in plain text

### User Experience

- Follow Material Design guidelines
- Provide feedback for user actions (loading indicators, error messages)
- Support dark theme
- Ensure accessibility (content descriptions, font scaling)
- Handle edge cases gracefully
- Support landscape and portrait orientations

## Kotlin Best Practices

### Use Kotlin Features

```kotlin
// Use data classes for model objects
data class User(
    val id: String,
    val name: String,
    val email: String
)

// Use extension functions to add functionality
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

// Use sealed classes for representing a limited set of types
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

// Use object for singletons
object DateFormatter {
    private const val DEFAULT_PATTERN = "yyyy-MM-dd"

    fun formatDate(date: Date, pattern: String = DEFAULT_PATTERN): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }
}

// Use lambdas for event handling
binding.btnSubmit.setOnClickListener { 
    viewModel.submitForm()
}

// Use let, apply, with, run, and also appropriately
user?.let { nonNullUser ->
    // Only execute if user is not null
    updateUI(nonNullUser)
}

binding.apply {
    // Avoid repeating binding. prefix
    tvTitle.text = title
    tvDescription.text = description
    btnAction.setOnClickListener { performAction() }
}
```

## Jetpack Compose Best Practices

### Composition

- Keep composables small and focused
- Use preview annotations for visual development
- Apply modifiers in a consistent order
- Use recomposition strategically
- Hoist state to appropriate levels

```kotlin
// Compose example with best practices
@Composable
fun TransactionScreen(
    viewModel: TransactionViewModel = hiltViewModel(),
    onTransactionClick: (String) -> Unit
) {
    // Collect UI state from ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Content composable with state hoisting
    TransactionContent(
        transactions = uiState.transactions,
        isLoading = uiState.isLoading,
        onRefresh = viewModel::refreshTransactions,
        onTransactionClick = onTransactionClick
    )
}

// Separate content from container for better testability
@Composable
private fun TransactionContent(
    transactions: List<Transaction>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onTransactionClick: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading && transactions.isEmpty()) {
            LoadingIndicator()
        } else {
            TransactionList(
                transactions = transactions,
                onTransactionClick = onTransactionClick,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Pull to refresh
        SwipeRefresh(
            state = rememberSwipeRefreshState(isLoading),
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {}
    }
}

// Reusable composable with preview
@Composable
private fun TransactionList(
    transactions: List<Transaction>,
    onTransactionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(transactions) { transaction ->
            TransactionItem(
                transaction = transaction,
                onClick = { onTransactionClick(transaction.id) }
            )
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    FlexiBankTheme {
        TransactionContent(
            transactions = listOf(
                Transaction("1", "Coffee", 4.99, System.currentTimeMillis()),
                Transaction("2", "Groceries", 65.43, System.currentTimeMillis())
            ),
            isLoading = false,
            onRefresh = {},
            onTransactionClick = {}
        )
    }
}
```

## Testing Best Practices

### Unit Testing

- Test ViewModels and business logic thoroughly
- Use fake repositories for testing ViewModels
- Keep tests small and focused
- Follow the AAA pattern (Arrange, Act, Assert)

```kotlin
@Test
fun `when login succeeds, emit success result`() = runTest {
    // Arrange
    val fakeRepository = FakeAuthRepository()
    fakeRepository.shouldSucceed = true
    val viewModel = LoginViewModel(fakeRepository)

    // Act
    viewModel.login("user@example.com", "password")

    // Assert
    val result = viewModel.loginResult.first()
    assertThat(result).isInstanceOf(Result.Success::class.java)
}
```

### UI Testing

- Test critical user flows
- Use test tags to identify elements
- Keep tests independent

```kotlin
@Test
fun loginScreenDisplaysErrorOnInvalidCredentials() {
    // Start the Login screen
    composeTestRule.setContent {
        FlexiBankTheme {
            LoginScreen(
                onLoginSuccess = {},
                viewModel = FakeLoginViewModel()
            )
        }
    }

    // Enter invalid credentials
    composeTestRule.onNodeWithTag("email_field").performTextInput("invalid")
    composeTestRule.onNodeWithTag("password_field").performTextInput("short")
    composeTestRule.onNodeWithTag("login_button").performClick()

    // Verify error message is displayed
    composeTestRule.onNodeWithTag("error_message").assertIsDisplayed()
    composeTestRule.onNodeWithText("Invalid email format").assertIsDisplayed()
}
```

## Dependency Injection with Hilt

```kotlin
// Application module
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) 
                    HttpLoggingInterceptor.Level.BODY else 
                    HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "flexibank_database"
        ).build()
    }
}

// Repository module
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: ApiService,
        userDao: UserDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): UserRepository {
        return UserRepositoryImpl(apiService, userDao, dispatcher)
    }
}

// Dispatcher module
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
```

## Coroutines and Flow

```kotlin
class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    // Return cold Flow that can be collected multiple times
    fun getUserProfile(userId: String): Flow<Result<User>> = flow {
        // Emit loading state
        emit(Result.Loading)

        try {
            // Try to get from local cache first
            val cachedUser = userDao.getUser(userId)
            if (cachedUser != null) {
                emit(Result.Success(cachedUser))
            }

            // Fetch fresh data from network
            val networkUser = apiService.getUser(userId)

            // Update cache
            userDao.insertUser(networkUser)

            // Emit success with fresh data
            emit(Result.Success(networkUser))
        } catch (e: Exception) {
            // Emit error state
            emit(Result.Error(e))
        }
    }.flowOn(dispatcher) // Execute on IO dispatcher

    // One-shot operation
    suspend fun updateUserProfile(user: User): Result<User> {
        return withContext(dispatcher) {
            try {
                val updatedUser = apiService.updateUser(user)
                userDao.updateUser(updatedUser)
                Result.Success(updatedUser)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
```
