# Coding Style Guide

This document demonstrates the preferred coding style with concrete examples for the FlexiBank Android application.

## ViewModel Implementation

```kotlin
@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val repository: ExampleRepository
) : ViewModel() {
    // State management with StateFlow
    private val _uiState = MutableStateFlow(ExampleUiState())
    val uiState: StateFlow<ExampleUiState> = _uiState.asStateFlow()

    // Error handling
    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents = _errorEvents.asSharedFlow()

    // Execute business logic in a coroutine
    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val result = repository.getData()
                result.onSuccess { data ->
                    _uiState.update { 
                        it.copy(isLoading = false, data = data) 
                    }
                }.onFailure { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    _errorEvents.emit(error.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _errorEvents.emit(e.message ?: "Unknown error")
            }
        }
    }
}

// UI state data class
data class ExampleUiState(
    val isLoading: Boolean = false,
    val data: List<ExampleData> = emptyList()
)
```

## Repository Interface

```kotlin
interface ExampleRepository {
    /**
     * Fetches data from remote or local source
     * @return Result containing list of data on success or exception on failure
     */
    suspend fun getData(): Result<List<ExampleData>>

    /**
     * Updates existing data
     * @param data The data to be updated
     * @return Result indicating success or failure
     */
    suspend fun updateData(data: ExampleData): Result<Boolean>
}
```

## Data Class

```kotlin
data class ExampleData(
    val id: String,
    val name: String,
    val createdAt: Long,
    val details: Map<String, Any> = mapOf()
)
```

## Constants

```kotlin
object Constants {
    // Network settings
    const val DEFAULT_TIMEOUT = 30L
    const val MAX_RETRY_COUNT = 3
    const val API_BASE_URL = "https://api.flexibank.com/"

    // Feature flags
    const val ENABLE_NEW_DASHBOARD = true

    // Pagination
    const val DEFAULT_PAGE_SIZE = 20
}
```

## Jetpack Compose UI Component

```kotlin
/**
 * A reusable card component for displaying account information
 * 
 * @param title The account name or title
 * @param balance The current account balance
 * @param currency The currency code
 * @param onClick Action to perform when card is clicked
 * @param modifier Optional modifier for customizing the layout
 */
@Composable
fun AccountCard(
    title: String,
    balance: Double,
    currency: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$currency ${balance.formatWithCommas()}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Extension function for formatting numbers
private fun Double.formatWithCommas(): String {
    return String.format("%,.2f", this)
}
```

## Extension Function

```kotlin
/**
 * Capitalizes the first letter of each word in a string
 * 
 * @return String with each word capitalized
 */
fun String.capitalizeWords(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercase() }
    }
}

/**
 * Safely parses a string to double with a default value if parsing fails
 * 
 * @param defaultValue Value to return if parsing fails
 * @return Parsed double or default value
 */
fun String?.toDoubleOrDefault(defaultValue: Double = 0.0): Double {
    return this?.toDoubleOrNull() ?: defaultValue
}
```

## Coroutine Usage

```kotlin
/**
 * Executes a network request with retry capability
 * 
 * @param T The type of data being returned
 * @param times Number of retry attempts
 * @param initialDelay Initial delay before first retry
 * @param block The suspend function to execute
 * @return Result containing data or exception
 */
suspend fun <T> executeWithRetry(
    times: Int = Constants.MAX_RETRY_COUNT,
    initialDelay: Long = 100,
    block: suspend () -> T
): Result<T> {
    var currentDelay = initialDelay
    repeat(times) { attempt ->
        try {
            return Result.success(block())
        } catch (e: Exception) {
            if (attempt == times - 1) {
                return Result.failure(e)
            }
            delay(currentDelay)
            currentDelay *= 2 // Exponential backoff
        }
    }
    // This line should never be reached given the return in the repeat block
    return Result.failure(IllegalStateException("Unknown error"))
}
```
